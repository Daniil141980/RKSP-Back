package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.EntityModelNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.NotEnoughRightsException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final FileService fileService;
    private final RoleService roleService;

    public Page<Account> getAllUsers(Integer pageNumber, Integer pageSize) {
        return accountRepository.findByRole_NameNot(Role.ROLE_ADMIN, PageRequest.of(pageNumber, pageSize, Sort.by("id")));
    }

    @Transactional
    public Account banUserById(Long id) throws EntityModelNotFoundException {
        var account = accountRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Пользователя", "id", Long.toString(id)));
        account.setIsBanned(true);
        return accountRepository.save(account);
    }

    @Transactional
    public Account unbanUserById(Long id) throws EntityModelNotFoundException {
        var account = accountRepository.findById(id).orElseThrow(() -> new EntityModelNotFoundException("Пользователя", "id", Long.toString(id)));
        account.setIsBanned(false);
        return accountRepository.save(account);
    }

    @Transactional
    public Account saveInformationAboutAccount(Account information, MultipartFile photo) throws EntityModelNotFoundException {
        var accountAuth = accountRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new EntityModelNotFoundException(
                        "Пользователя",
                        "почтой",
                        SecurityContextHolder.getContext().getAuthentication().getName())
        );
        if (accountAuth.getNickname().equals(information.getNickname())) {
            accountAuth.setDescription(information.getDescription());
            accountAuth.setFirstName(information.getFirstName());
            accountAuth.setLastName(information.getLastName());
            accountAuth.setMiddleName(information.getMiddleName());
            accountAuth.setBirthDate(information.getBirthDate());
            if (photo != null) {
                var photoInProfile = fileService.store(photo);
                if (accountAuth.getProfilePhoto() != null) {
                    accountAuth.getProfilePhoto().setFileNameInDirectory(photoInProfile.getFileNameInDirectory());
                    accountAuth.getProfilePhoto().setInitialFileName(photoInProfile.getInitialFileName());
                } else {
                    accountAuth.setProfilePhoto(photoInProfile);
                }
            }
        } else {
            throw new NotEnoughRightsException();
        }
        return accountRepository.save(accountAuth);
    }

    @Transactional
    public Account getAccount(String nickname) throws EntityModelNotFoundException {
        return accountRepository.findByNickname(nickname).orElseThrow(() ->
                new EntityModelNotFoundException("Пользователя", "nickname", nickname)
        );
    }

    @Transactional
    public Account getAccountByEmail(String email) throws EntityModelNotFoundException {
        return accountRepository.findByEmail(email).orElseThrow(() ->
                new EntityModelNotFoundException("Пользователя", "почтой", email)
        );
    }

    @Transactional
    public Account updateAccountRole(Long accountId, Long roleId) throws RoleNotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new EntityModelNotFoundException("Пользователя", "id", String.valueOf(accountId))
        );
        RoleEntity role = roleService.getRoleById(roleId);
        if (Role.ROLE_ADMIN.equals(role.getName())) {
            throw new NotEnoughRightsException();
        }
        account.setRole(role);
        return account;
    }
}
