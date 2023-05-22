package com.decagon.fashionBlog.controller;

import com.decagon.fashionBlog.dto.CommentsDTO;
import com.decagon.fashionBlog.entity.Comments;
import com.decagon.fashionBlog.entity.Posts;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.exceptions.NoPermissionExceptions;
import com.decagon.fashionBlog.serviceImpl.PostsServiceImpl;
import com.decagon.fashionBlog.serviceImpl.UsersServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/visitors")
public class VisitorsController {
    private final PostsServiceImpl postsServiceImpl;
    private final UsersServiceImpl usersServiceImpl;


    @PutMapping("/comments/{post_id}")
    public ResponseEntity<Comments> commentPost(@PathVariable Long post_id, @NonNull @RequestBody CommentsDTO commentDto
            ,SecurityContextHolder securityContextHolder){

        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is a Visitor
        if( !role.equals("[VISITOR]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok(postsServiceImpl.addComment(post_id,commentDto));
    }

    @PutMapping("/posts/likes/{post_id}")
    public ResponseEntity<Posts> likePost( @PathVariable Long post_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is a Visitor
        if( !role.equals("[VISITOR]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok(postsServiceImpl.likePost(post_id));
    }

    @PutMapping("/posts/remove-likes/{post_id}")
    public ResponseEntity<Posts> removeLike(@PathVariable Long post_id, SecurityContextHolder securityContextHolder){
        // get the user details from the securityContextHolder
        String role = securityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        //check if the user is a Visitor
        if( !role.equals("[VISITOR]")){
            throw new NoPermissionExceptions("User Inactive or User do not have permission");
        }

        return ResponseEntity.ok(postsServiceImpl.removeLike(post_id));
    }
}
