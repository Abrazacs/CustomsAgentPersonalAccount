package ru.ssemenov.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ssemenov.entities.Role;
import ru.ssemenov.repositories.RoleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    List<Role> findAllByName(List<String> names){
        return roleRepository.findAllByName(names);
    }
}
