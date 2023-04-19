package com.decagon.fashionblog.service;

import com.decagon.fashionblog.entity.Posts;
import com.decagon.fashionblog.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    Users addPost(Users user, Posts post);
    public Users getAdminUser(Long id);
    public Users getVisitorsUser(Long id);
    List<Users> getAllUsers();
    Users addUser(Users user);

}
