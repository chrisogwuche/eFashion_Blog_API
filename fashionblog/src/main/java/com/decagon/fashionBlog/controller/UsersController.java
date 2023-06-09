package com.decagon.fashionBlog.controller;

import com.decagon.fashionBlog.dto.UsersDTO;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.service.UsersService;
import com.decagon.fashionBlog.serviceImpl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private  final UsersService userService;

    @Autowired
    public UsersController(UsersServiceImpl usersService){
        this.userService = usersService;
    }

    @GetMapping
    public List<Users> allUser(){
        return userService.getAllUsers();
    }

}
