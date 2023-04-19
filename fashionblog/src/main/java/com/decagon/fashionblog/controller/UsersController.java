package com.decagon.fashionblog.controller;

import com.decagon.fashionblog.entity.Users;
import com.decagon.fashionblog.serviceImpl.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private  final UsersServiceImpl userServiceImpl;

    @Autowired
    public UsersController(UsersServiceImpl usersServiceImpl){
        this.userServiceImpl = usersServiceImpl;
    }

    @GetMapping
    public List<Users> allUser(){
        return userServiceImpl.getAllUsers();
    }

    @PostMapping("/add")
    public Users addUser(@RequestBody Users user){
        return userServiceImpl.addUser(user);
    }
}
