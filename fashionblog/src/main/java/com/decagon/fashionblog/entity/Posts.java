package com.decagon.fashionblog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
public class Posts {
    @Id
    @SequenceGenerator(
            name = "posts_sequence",
            sequenceName = "posts_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "posts_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    @Column(nullable = false)
    private String price;
    private int likes;
    private LocalDateTime createdAt;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    @JsonIgnoreProperties("post")
    private List<Comments> commentList = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties("postList")
    private Users users;


    public Posts(String title,String body,String price){
        this.title = title;
        this.body = body;
        this.price = price;
        this.likes = 0;
    }

    public void addComment(Comments comment){
        commentList.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comments comment){
        if(comment!=null){
            commentList.remove(comment);
        }
        comment.setPost(null);

    }

    public void addLikes(){
        likes+=1;
    }

    public void removeLikes(){
        if(likes !=0){
            likes -= 1;
        }

    }
}
