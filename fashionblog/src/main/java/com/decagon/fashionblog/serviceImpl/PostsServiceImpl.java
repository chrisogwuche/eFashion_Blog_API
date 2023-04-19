package com.decagon.fashionblog.serviceImpl;

import com.decagon.fashionblog.entity.Comments;
import com.decagon.fashionblog.entity.Posts;
import com.decagon.fashionblog.entity.Users;
import com.decagon.fashionblog.repository.PostsRepository;
import com.decagon.fashionblog.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class PostsServiceImpl implements PostsService {
    final private PostsRepository repo;
    @Autowired
    public PostsServiceImpl(PostsRepository repo){
        this.repo = repo;
    }

    @Override
    public List<Posts> getAllPosts() {
        return repo.findAll();
    }

    @Override
    public Posts getPostById(Long postId) {
        Optional<Posts> optPost = repo.findById(postId);
        if(optPost.isEmpty()){
            throw new IllegalStateException("Post not Found");
        }

        return optPost.get();
    }

    @Override
    public void deletePost(Long postId) {
        Optional<Posts> optPost = repo.findById(postId);
        if(optPost.isEmpty()){
            throw new NullPointerException("post not found");
        }
        Posts post = optPost.get();
        // set users to null because i want to only delete the post
        // and if it is not set to null it will delete both the post and the user because the cascading type is All
        post.setUsers(null);
        repo.delete(post);
    }

    @Override
    public void addComment(Long postId, Comments comment) {
        Optional<Posts> optPost = repo.findById(postId);
        if(optPost.isEmpty()){
            throw new NullPointerException("Post cannot be commented because post not found");
        }
        Posts post = optPost.get();
        post.addComment(comment);
        repo.save(post);
    }

    @Override
    public void likePost(Long postId) {
        Optional<Posts> optPost = repo.findById(postId);
        if(optPost.isEmpty()){
            throw new NullPointerException("cannot like because post not found");
        }

        Posts post = optPost.get();
        post.addLikes();
        repo.save(post);
    }

    @Override
    public void removeLike(Long postId) {
        Optional<Posts> optPost = repo.findById(postId);
        if(optPost.isEmpty()){
            throw new NullPointerException("cannot remove like because post not found");
        }

        Posts post = optPost.get();
        post.removeLikes();
        repo.save(post);
    }
}
