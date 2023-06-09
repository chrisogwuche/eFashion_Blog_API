package com.decagon.fashionBlog.configuration;

import com.decagon.fashionBlog.repository.TokenRepository;
import com.decagon.fashionBlog.repository.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;
    private  final JwtService jwtService;
    private final UsersRepository usersRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return; // no action will take place
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        var savedUser = usersRepository.findByEmail(userEmail)
                .orElse(null);
        var savedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if(savedToken==null && savedUser==null){
            return;
        }

        savedToken.setExpired(true);
        savedToken.setRevoked(true);
        tokenRepository.save(savedToken);
        //save the user back into the database and set loggedIn to false
        savedUser.setLoggedIn(false);
        usersRepository.save(savedUser);


    }
}
