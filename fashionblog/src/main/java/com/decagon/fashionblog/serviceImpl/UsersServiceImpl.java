package com.decagon.fashionblog.serviceImpl;

import com.decagon.fashionblog.entity.Posts;
import com.decagon.fashionblog.entity.Users;
import com.decagon.fashionblog.enums.Role;
import com.decagon.fashionblog.enums.Status;
import com.decagon.fashionblog.repository.UsersRepository;
import com.decagon.fashionblog.service.UsersService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repo;
    public UsersServiceImpl(UsersRepository repo){
        this.repo = repo;
    }


    @Override
    public Users getAdminUser(Long id){
        Optional<Users> optUser = repo.findById(id);
        if(optUser.isEmpty()){
            throw new IllegalStateException("user Not found");
        }

        Users user = optUser.get();
        //check if the user is an admin
        if(user.getStatus() != Status.ACTIVE || user.getRole() != Role.ADMIN){
            throw new IllegalStateException("Inactive or no permission");
        }
        return user;
    }

    @Override
    public Users getVisitorsUser(Long id){
        Optional<Users> optUser = repo.findById(id);
        if(optUser.isEmpty()){
            throw new IllegalStateException("user Not found");
        }

        Users user = optUser.get();
        //check if the user is an admin
        if(user.getStatus() != Status.ACTIVE || user.getRole() != Role.VISITOR){
            throw new IllegalStateException("Inactive or no permission");
        }
        return user;
    }

    @Override
    public Users addPost(Users user, Posts post) {
        post.setCreatedAt(LocalDateTime.now());
        user.addPost(post);
        return repo.save(user);
    }

    @Override
    public List<Users> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Users addUser(Users user) {
        user.setRole(user.getRole());
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        return repo.save(user);
    }



}
