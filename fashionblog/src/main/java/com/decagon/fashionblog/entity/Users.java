package com.decagon.fashionblog.entity;

import com.decagon.fashionblog.enums.Role;
import com.decagon.fashionblog.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_email_unique",
                        columnNames = "email" )
        })

public class Users {
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

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "users")
    @JsonIgnoreProperties("users")
    private List<Posts> postList = new ArrayList<>();

//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "user_id",referencedColumnName = "id")
//    private List<Comments> commentList =new ArrayList<>();

    public Users(String firstName,String lastName,String email,String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
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

//    public void addComment(Comments comment){
//        commentList.add(comment);
//    }
//
//    public void removeComment(Comments comment){
//        if(comment!= null){
//            commentList.remove(comment);
//        }
//    }

}
