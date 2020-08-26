package com.jg.FindFafik.persistence.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
//    @Size(min=2, max=30)
    private String username;

//    @Size(min=2, max=30)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_privileges", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns =
    @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns =
    @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns =
    @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;

    public User(String username, String password) {
        this.username=username;
        this.password=password;
    }
}
