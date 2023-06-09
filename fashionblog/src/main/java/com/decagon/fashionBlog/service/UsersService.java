package com.decagon.fashionBlog.service;

import com.decagon.fashionBlog.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    List<Users> getAllUsers();

    Users deleteVisitor(Long visitorId);

    Users logoutUser(Long id);
    Users getUserByEmail(String email);
}
