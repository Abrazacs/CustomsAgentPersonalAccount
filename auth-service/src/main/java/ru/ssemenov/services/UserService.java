package ru.ssemenov.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.ssemenov.converters.UserDtoToUserConverter;
import ru.ssemenov.dtos.ExportUserDto;
import ru.ssemenov.dtos.UserDto;
import ru.ssemenov.entities.Role;
import ru.ssemenov.entities.User;
import ru.ssemenov.exceptions.NotFoundException;
import ru.ssemenov.exceptions.RegistrationException;
import ru.ssemenov.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDtoToUserConverter userDtoToUserConverter;
    private final RoleService roleService;

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<ExportUserDto> findUsersByCompanyVAT(String companyVAT) {
        List<ExportUserDto> users = userRepository.findAllByCompanyVAT(companyVAT).
                stream()
                    .map(u -> ExportUserDto
                            .builder()
                            .id(u.getId())
                            .username(u.getUsername())
                            .email(u.getEmail())
                            .build()
                        )
                    .collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new NotFoundException("No entries found with this companyVAT");
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with username=%s not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    public void addUser(String vatCode, UserDto userDto) {
        UUID trace = UUID.randomUUID();
        log.info("Start create new user userDto={} for vatCode={}, trace={}", userDto, vatCode, trace);
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            log.error("Error create user, username={} is occupied, trace={}", userDto.getUsername(), trace);
            throw new RegistrationException("This username is occupied. Try to use another one");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            log.error("Error create user, email={} is occupied, trace={}", userDto.getEmail(), trace);
            throw new RegistrationException("This email is occupied. Try to use another one");
        }
        log.info("Load roles={} for user, trace={}", userDto.getRolesNames(), trace);
        List<Role> roles = roleService.findAllByNames(userDto.getRolesNames());
        User user = userDtoToUserConverter.convert(userDto, vatCode, roles);
        userRepository.save(user);
        log.info("User successfully saved, traceId={}", trace);
    }

    public void deleteUser(UUID id) {
        UUID trace = UUID.randomUUID();
        log.info("Start delete user id={}, traceId={}", id, trace);
        try {
            userRepository.deleteById(id);
            log.info("User with id={} successfully deleted, traceId={}", id, trace);
        } catch (IllegalArgumentException e) {
            log.error("Error delete user with id={}, error={}, traceId={}", id, e.getMessage(), trace);
            throw new NotFoundException("No entry found with this id");
        }
    }
}
