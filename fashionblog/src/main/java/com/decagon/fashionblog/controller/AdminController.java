package com.decagon.fashionblog.controller;

import com.decagon.fashionblog.entity.Posts;
import com.decagon.fashionblog.entity.Users;
import com.decagon.fashionblog.serviceImpl.PostsServiceImpl;
import com.decagon.fashionblog.serviceImpl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admins")
public class AdminController {
    final private PostsServiceImpl postsServiceImpl;
    final private UsersServiceImpl usersServiceImpl;
    @Autowired
    public AdminController(PostsServiceImpl postsServiceImpl, UsersServiceImpl usersServiceImpl){
        this.postsServiceImpl = postsServiceImpl;
        this.usersServiceImpl = usersServiceImpl;
    }

    //get all post
    @GetMapping("/all-post")
    public List<Posts> allPosts(){
        return postsServiceImpl.getAllPosts();
    }

    //find post
    @GetMapping("/{id}/posts/{post_id}")
    public Posts findPost(@PathVariable Long id,@PathVariable Long post_id){
        Users user = usersServiceImpl.getAdminUser(id);

        return postsServiceImpl.getPostById(post_id);
    }

    // Admin to add post
    @PutMapping("/{id}/posts/add")
    public Users addPost(@NonNull @RequestBody Posts post, @NonNull @PathVariable Long id ){
//        HttpSession session = request.getSession();
//        Long id = (Long) session.getAttribute("id");
        Users user = usersServiceImpl.getAdminUser(id);
        return usersServiceImpl.addPost(user,post);
    }

    //delete post
    @DeleteMapping("/{id}/deletes/{post_id}")
    public void deletePost(@PathVariable Long id, @PathVariable Long post_id){
        Users users = usersServiceImpl.getAdminUser(id);

        postsServiceImpl.deletePost(post_id);

    }



}
