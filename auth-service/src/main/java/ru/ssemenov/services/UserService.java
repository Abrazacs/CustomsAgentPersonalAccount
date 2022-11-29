package ru.ssemenov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ssemenov.dtos.UserDto;
import ru.ssemenov.entities.Role;
import ru.ssemenov.entities.User;
import ru.ssemenov.exceptions.RegistrationException;
import ru.ssemenov.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
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
        User user = userDtoToUser(vatCode, userDto);
        userRepository.save(user);
    }

    private User userDtoToUser(String vatCode, UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .companyVAT(vatCode)
                .roles(roleService.findAllByNames(userDto.getRolesNames()))
                .build();
    }
}
