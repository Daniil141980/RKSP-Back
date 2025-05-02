package edu.tinkoff.tinkoffbackendacademypetproject.services;

import edu.tinkoff.tinkoffbackendacademypetproject.exceptions.RoleNotFoundException;
import edu.tinkoff.tinkoffbackendacademypetproject.model.Role;
import edu.tinkoff.tinkoffbackendacademypetproject.model.RoleEntity;
import edu.tinkoff.tinkoffbackendacademypetproject.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public RoleEntity save(RoleEntity role) {
        return roleRepository.save(role);
    }

    public RoleEntity getRoleByName(Role role) throws RoleNotFoundException {
        return roleRepository.findByName(role).orElseThrow(() -> new RoleNotFoundException(role));
    }

    public RoleEntity getRoleById(Long id) throws RoleNotFoundException {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
    }

    @Transactional
    public boolean roleExists(Role role) {
        return roleRepository.existsByName(role);
    }

    public List<RoleEntity> getAllRolesInsteadOfAdmin() {
        return roleRepository.findByNameNot(Role.ROLE_ADMIN);
    }

}
