//package com.decagon.fashionblog.entity;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//public class Likes {
//    @Id
//    @SequenceGenerator(
//            name = "likes_sequence",
//            sequenceName = "likes_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "likes_sequence"
//    )
//    private Long id;
//    @ManyToMany
//    @JoinTable(name = "user_Likes_post",
//                joinColumns = @JoinColumn(name = "user_id"),
//                inverseJoinColumns = @JoinColumn(name = "post_id"))
//    @JsonIgnoreProperties("likes")
//    private List<Posts> likedPosts = new ArrayList<>();
//
//}
