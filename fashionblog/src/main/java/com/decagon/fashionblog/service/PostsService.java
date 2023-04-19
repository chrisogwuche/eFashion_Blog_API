package com.decagon.fashionblog.service;

import com.decagon.fashionblog.entity.Comments;
import com.decagon.fashionblog.entity.Posts;
import com.decagon.fashionblog.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostsService {
    public List<Posts> getAllPosts();
    Posts getPostById(Long postId);
    void deletePost(Long postId);

    void addComment(Long postId, Comments comment);

    void likePost(Long postId);

    void removeLike(Long postId);
}
