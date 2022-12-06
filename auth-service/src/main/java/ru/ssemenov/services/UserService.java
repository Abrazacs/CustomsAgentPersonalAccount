package ru.ssemenov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserDtoToUserConverter userDtoToUserConverter;


    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<ExportUserDto> findUsersByCompanyVAT(String companyVAT) {
        List<ExportUserDto> users = userRepository.findAllByCompanyVAT(companyVAT).
                stream()
                    .map(u ->
                            ExportUserDto.builder()
                                .username(u.getUsername())
                                .email(u.getEmail())
                                .build())
                    .collect(Collectors.toList());

        if (users.isEmpty()) {
            throw new NotFoundException("No entry found with this companyVAT");
        }
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with login '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    public void addUser(String vatCode, UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new RegistrationException("This login is occupied. Try to use another one");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RegistrationException("This email is occupied. Try to use another one");
        }
        User user = userDtoToUserConverter.convert(userDto, vatCode);
        userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        try {
            userRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("No entry found with this id");
        }
    }


}
