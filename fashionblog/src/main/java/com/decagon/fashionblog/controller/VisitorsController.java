package com.decagon.fashionblog.controller;

import com.decagon.fashionblog.entity.Comments;
import com.decagon.fashionblog.entity.Users;
import com.decagon.fashionblog.serviceImpl.PostsServiceImpl;
import com.decagon.fashionblog.serviceImpl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/visitors")
public class VisitorsController {

    private final UsersServiceImpl usersServiceImpl;
    private final PostsServiceImpl postsServiceImpl;

    @Autowired
    public VisitorsController(UsersServiceImpl usersServiceImpl, PostsServiceImpl postsServiceImpl){
        this.usersServiceImpl = usersServiceImpl;
        this.postsServiceImpl = postsServiceImpl;
    }

    @PutMapping("/{id}/comments/{post_id}")
    public void commentPost(@PathVariable Long id, @PathVariable Long post_id, @NonNull @RequestBody Comments comment){
        Users user = usersServiceImpl.getVisitorsUser(id);

        postsServiceImpl.addComment(post_id,comment);

    }

    @PutMapping("/{id}/posts/likes/{post_id}")
    public void likePost(@PathVariable Long id,@PathVariable Long post_id){
        Users user = usersServiceImpl.getVisitorsUser(id);

        postsServiceImpl.likePost(post_id);
    }
    @PutMapping("/{id}/posts/deletes/{post_id}")
    public void removeLike(@PathVariable Long id,@PathVariable Long post_id){
        Users user = usersServiceImpl.getVisitorsUser(id);

        postsServiceImpl.removeLike(post_id);
    }
}
