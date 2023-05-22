package com.decagon.fashionBlog.service;

import com.decagon.fashionBlog.dto.CommentsDTO;
import com.decagon.fashionBlog.dto.PostsDTO;
import com.decagon.fashionBlog.entity.Comments;
import com.decagon.fashionBlog.entity.Posts;
import com.decagon.fashionBlog.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostsService {
    public List<Posts> getAllPosts();

    Posts getPostById(Long postId);

    List<Posts> deletePost(Long postId);

    Comments addComment(Long postId, CommentsDTO commentDto);

    Posts likePost(Long postId);

    Posts removeLike(Long postId);

    Posts addPost(Users user, PostsDTO postDto);
}
