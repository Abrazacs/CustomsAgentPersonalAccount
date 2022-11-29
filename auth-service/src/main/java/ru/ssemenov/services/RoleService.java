package ru.ssemenov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ssemenov.entities.Role;
import ru.ssemenov.repositories.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    List<Role> findAllByNames(List<String> names) {
        return roleRepository.findAllByNameIn(names);
    }
}
