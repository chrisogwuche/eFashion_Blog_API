package com.decagon.fashionBlog.entity;

import com.decagon.fashionBlog.enums.Role;
import com.decagon.fashionBlog.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_email_unique",
                        columnNames = "email" )
        })

public class Users implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createdAt;
    private boolean loggedIn = false;

    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    @JsonIgnoreProperties("user")
    private List<Token> tokenList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "users")
    @JsonIgnoreProperties("users")  // To avoid circular dependency error
    private List<Posts> postList = new ArrayList<>();

    public Users(String firstName,String lastName,Role role,String email,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.email = email;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.status = Status.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(status == Status.ACTIVE){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isLoggedIn();
    }

    public void addPost(Posts post){
        postList.add(post);
        post.setUsers(this);
    }

    public void removePost(Posts post){
        if(post != null){
            postList.remove(post);
        }
        post.setUsers(null);
    }

}
