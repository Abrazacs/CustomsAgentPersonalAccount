package ru.ssemenov.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ssemenov.repositories.RoleRepository;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;


}
