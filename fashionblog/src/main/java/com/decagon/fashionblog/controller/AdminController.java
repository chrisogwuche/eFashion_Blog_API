package com.decagon.fashionBlog.controller;

import com.decagon.fashionBlog.dto.PostsDTO;
import com.decagon.fashionBlog.entity.Posts;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.exceptions.NoPermissionExceptions;
import com.decagon.fashionBlog.serviceImpl.PostsServiceImpl;
import com.decagon.fashionBlog.serviceImpl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admins")
public class AdminController {
    final private PostsServiceImpl postsServiceImpl;
    final private UsersServiceImpl usersServiceImpl;


    @GetMapping("/all-post")
    public ResponseEntity<List<Posts>> allPosts(SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok(postsServiceImpl.getAllPosts());
    }

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<Posts> findPost(@PathVariable Long post_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok(postsServiceImpl.getPostById(post_id));
    }

    @PutMapping("/posts/add")
    public ResponseEntity<Posts> addPost(@NonNull @RequestBody PostsDTO postDto, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        UserDetails userDetails = (UserDetails) securityContextHolder.getContext().getAuthentication().getPrincipal();
       //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        Users user = usersServiceImpl.getUserByEmail(userDetails.getUsername());

        return ResponseEntity.ok(postsServiceImpl.addPost(user,postDto));
    }

    @DeleteMapping("/deletes/posts/{post_id}")
    public ResponseEntity<List<Posts>> deletePost( @PathVariable Long post_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok( postsServiceImpl.deletePost(post_id));
    }

    @DeleteMapping("/deletes/visitor/{visitor_id}")
    public ResponseEntity<Users> deleteVisitor(@PathVariable Long visitor_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok( usersServiceImpl.deleteVisitor(visitor_id));
    }

}
