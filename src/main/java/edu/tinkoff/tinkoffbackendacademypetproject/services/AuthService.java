package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.AccountAlreadyExistException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.BannedAccountException;
import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.LoginFailException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Account;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.AccountRepository;
import edu.tinkoff.tinkoffbackendacademypetproject.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Transactional
    public Pair<Account, String> login(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            Account account = accountRepository.findByEmail(email).orElseThrow(LoginFailException::new);

            if (Boolean.TRUE.equals(account.getIsBanned())) {
                throw new BannedAccountException();
            }
            return Pair.of(account, jwtService.generateToken(account));
        } catch (AuthenticationException e) {
            throw new LoginFailException();
        }
    }

    @Transactional
    public Pair<Account, String> register(String email, String nickname, String password) throws AccountAlreadyExistException {
        if (accountRepository.findByEmail(email).isPresent()) {
            throw new AccountAlreadyExistException("почтой", email);
        }
        if (accountRepository.findByNickname(nickname).isPresent()) {
            throw new AccountAlreadyExistException("ником", nickname);
        }
        var account = new Account();
        account.setEmail(email);
        account.setNickname(nickname);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole(roleService.getRoleByName(Role.ROLE_USER));
        account.setIsBanned(false);
        return Pair.of(account, jwtService.generateToken(accountRepository.save(account)));
    }

    @Transactional
    public void registerAdmin(Account account) throws AccountAlreadyExistException {
        if (accountRepository.findByEmail(account.getEmail()).isEmpty()) {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.save(account);
        }
    }
}
