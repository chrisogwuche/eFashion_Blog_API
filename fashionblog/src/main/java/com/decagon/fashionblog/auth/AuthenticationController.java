package com.decagon.fashionBlog.auth;

import com.decagon.fashionBlog.dto.LoginDTO;
import com.decagon.fashionBlog.dto.UsersDTO;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.repository.UsersRepository;
import com.decagon.fashionBlog.serviceImpl.UsersServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UsersServiceImpl userServiceImpl;
    private final UsersRepository repository;


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@NonNull @RequestBody UsersDTO usersDTO){
        return ResponseEntity.ok(service.register(usersDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@NonNull @RequestBody LoginDTO loginDTO){
        return ResponseEntity.ok(service.authenticate(loginDTO));
    }

    @PostMapping("/logout")
    public  void logout(){
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<Users>> allUser(){
        return ResponseEntity.ok(userServiceImpl.getAllUsers());
    }


}
