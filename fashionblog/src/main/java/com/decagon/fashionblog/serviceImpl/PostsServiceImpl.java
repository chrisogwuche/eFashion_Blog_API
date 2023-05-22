package com.decagon.fashionBlog.serviceImpl;

import com.decagon.fashionBlog.dto.CommentsDTO;
import com.decagon.fashionBlog.dto.PostsDTO;
import com.decagon.fashionBlog.entity.Comments;
import com.decagon.fashionBlog.entity.Posts;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.exceptions.PostNotFoundExceptions;
import com.decagon.fashionBlog.repository.PostsRepository;
import com.decagon.fashionBlog.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class PostsServiceImpl implements PostsService {
    final private PostsRepository repo;


    @Override
    public List<Posts> getAllPosts() {
        return repo.findAll();
    }

    @Override
    public Posts addPost(Users user, PostsDTO postDto){
        var post = Posts.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .price(postDto.getPrice())
                .likes(0)
                .createdAt(LocalDateTime.now())
                .build();

        post.setUsers(user);

        return repo.save(post);
    };

    @Override
    public Posts getPostById(Long postId) {
        Posts post = authPosts(postId);
        return post;
    }

    @Override
    public List<Posts> deletePost(Long postId) {
        Posts post = authPosts(postId);

        // set users to null because I want to only delete the post
        // and if it is not set to null it will delete both the post and the user because the cascading type is All
        post.setUsers(null);
        repo.delete(post);

        //get all the post by calling the getAllPosts method defined above
        return getAllPosts();
    }

    @Override
    public Comments addComment(Long postId, CommentsDTO commentDto) {
        Posts post = authPosts(postId);
        var comment = Comments.builder()
                .comment(commentDto.getComment())
                .build();

        post.addComment(comment);
        repo.save(post);

        return comment;
    }

    @Override
    public Posts likePost(Long postId) {
        Posts post = authPosts(postId);
        post.addLikes();
        repo.save(post);

        return post;
    }

    @Override
    public Posts removeLike(Long postId) {
        Posts post = authPosts(postId);
        post.removeLikes();
        repo.save(post);

        return post;
    }

    public Posts authPosts(Long postId){
        Posts post = repo.findById(postId)
                .orElseThrow(()->
                        new PostNotFoundExceptions("Post not found with id: "+postId));

        return post;
    }
}
