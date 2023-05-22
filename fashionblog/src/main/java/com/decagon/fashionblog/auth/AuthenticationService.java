package com.decagon.fashionBlog.auth;

import com.decagon.fashionBlog.configuration.JwtService;
import com.decagon.fashionBlog.dto.LoginDTO;
import com.decagon.fashionBlog.dto.UsersDTO;
import com.decagon.fashionBlog.entity.Token;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.enums.TokenType;
import com.decagon.fashionBlog.exceptions.UsersNotFoundExceptions;
import com.decagon.fashionBlog.repository.TokenRepository;
import com.decagon.fashionBlog.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse register(UsersDTO usersDTO) {
        Users user = new Users(usersDTO.getFirstname(), usersDTO.getLastname(), usersDTO.getRole(), usersDTO.getEmail(),
                passwordEncoder.encode(usersDTO.getPassword()));

        //save to database
        repository.save(user);

        var jwtToken = jwtService.generateToken(user);
        saveToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(LoginDTO request) {

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(()->
                        new UsersNotFoundExceptions("User with email: " +request.getEmail() +" not found"));
        user.setLoggedIn(true);

        repository.save(user);

        //UsernamePasswordAuthenticationToken class has to input parameters; Object principal and Object credential
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        revokeToken(user);
        var jwtToken = jwtService.generateToken(user);
        saveToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void saveToken(Users user, String jwtToken) {
        var token = Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(token);
    }

    private void revokeToken(Users user){
        var validTokenByUser= tokenRepository.findAllValidTokenByUser(user.getId());
        if(validTokenByUser.isEmpty())
            return;
        validTokenByUser.forEach(t->{
            t.setRevoked(true);
            t.setExpired(true);
        });

        tokenRepository.saveAll(validTokenByUser);
    }
}
