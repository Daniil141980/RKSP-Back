package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.FileEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.NewsPublicationEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.PublicationEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.NewsPublicationRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.PublicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsPublicationService {
    private final NewsPublicationRepository newsPublicationRepository;
    private final PublicationRepository publicationRepository;
    private final FileService fileService;

    @Transactional
    public PublicationEntity createPublicationInNews(PublicationEntity publication, Account account, List<MultipartFile> files) throws EntityModelNotFoundException {
        if (files != null) {
            var filesInPublication = new HashSet<FileEntity>();
            for (var file : files) {
                var fileInPublication = fileService.store(file);
                filesInPublication.add(fileInPublication);
            }
            publication.setFiles(filesInPublication);
        } else {
            publication.setFiles(new HashSet<>());
        }
        publication.setSupportsThread(true);
        publication.setAccount(account);
        var savedPublication = publicationRepository.save(publication);
        newsPublicationRepository.save(new NewsPublicationEntity(savedPublication.getId(), savedPublication));
        return savedPublication;
    }

    public Page<PublicationEntity> getPublicationsInNews(Integer pageNumber, Integer pageSize) {
        return newsPublicationRepository.findAll(PageRequest.of(pageNumber, pageSize, Sort.by("id").reverse())).map(NewsPublicationEntity::getPublication);
    }
}
