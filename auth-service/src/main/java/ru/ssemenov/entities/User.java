package ru.ssemenov.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id") )
    private List<Role> roles;

    @Column(name = "company_vat")
    private String companyVAT;

    @Column(name = "email")
    private String email;


}
