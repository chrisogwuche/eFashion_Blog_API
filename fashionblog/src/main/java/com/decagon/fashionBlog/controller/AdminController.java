package com.decagon.fashionBlog.controller;

import com.decagon.fashionBlog.dto.PostsDTO;
import com.decagon.fashionBlog.entity.Posts;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.exceptions.NoPermissionExceptions;
import com.decagon.fashionBlog.service.PostsService;
import com.decagon.fashionBlog.service.UsersService;
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
    final private PostsService postsService;
    final private UsersService usersService;


    @GetMapping("/all-post")
    public ResponseEntity<List<Posts>> allPosts(SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok(postsService.getAllPosts());
    }

    @GetMapping("/posts/{post_id}")
    public ResponseEntity<Posts> findPost(@PathVariable Long post_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok(postsService.getPostById(post_id));
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

        Users user = usersService.getUserByEmail(userDetails.getUsername());

        return ResponseEntity.ok(postsService.addPost(user,postDto));
    }

    @DeleteMapping("/deletes/posts/{post_id}")
    public ResponseEntity<List<Posts>> deletePost( @PathVariable Long post_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok( postsService.deletePost(post_id));
    }

    @DeleteMapping("/deletes/visitor/{visitor_id}")
    public ResponseEntity<Users> deleteVisitor(@PathVariable Long visitor_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is an Admin
        if( !role.equals("[ADMIN]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok( usersService.deleteVisitor(visitor_id));
    }

}
