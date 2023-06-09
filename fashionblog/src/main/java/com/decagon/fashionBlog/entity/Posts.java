package com.decagon.fashionBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Builder
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
    private String description;
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


//    public Posts(String title,String description,String price){
//        this.title = title;
//        this.description = description;
//        this.price = price;
//        this.likes = 0;
//        this.createdAt = LocalDateTime.now();
//    }

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
