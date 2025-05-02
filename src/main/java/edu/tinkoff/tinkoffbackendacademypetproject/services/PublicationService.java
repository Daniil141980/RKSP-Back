package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.FileEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.PublicationEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.model.SubjectTopicEntity;
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
public class PublicationService {
    private final PublicationRepository publicationRepository;
    private final SubjectTopicService subjectTopicService;
    private final FileService fileService;

    @Transactional
    public PublicationEntity createPublicationInSubjectTopic(PublicationEntity publication, Account account, List<MultipartFile> files) throws EntityModelNotFoundException {
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
        var subjectTopic = new HashSet<SubjectTopicEntity>();
        subjectTopic.add(subjectTopicService.getSubjectTopic(publication.getSubjectTopics().iterator().next().getId()));
        publication.setSubjectTopics(subjectTopic);
        publication.setSupportsThread(false);
        publication.setAccount(account);
        return publicationRepository.save(publication);
    }

    public PublicationEntity getPublication(Long id) throws EntityModelNotFoundException {
        return publicationRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Публикации", "id", Long.toString(id)));
    }

    @Transactional
    public void deletePublication(Long id) throws EntityModelNotFoundException {
        publicationRepository.delete(getPublication(id));
    }

    public Page<PublicationEntity> getPublicationsInOneCategory(Integer pageNumber, Integer pageSize, Long subjectTopicId) {
        return publicationRepository.findBySubjectTopics_Id(subjectTopicId, PageRequest.of(pageNumber, pageSize, Sort.by("id")));
    }
}