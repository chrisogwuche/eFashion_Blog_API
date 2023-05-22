package com.decagon.fashionBlog.configuration;

import com.decagon.fashionBlog.entity.Token;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.repository.TokenRepository;
import com.decagon.fashionBlog.repository.UsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private  final JwtService jwtService;
    private  final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {



        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return; // no action will take place
        }

        jwt = authHeader.substring(7);
        

        //extract the userEmail from jwt token
        userEmail = jwtService.extractUsername(jwt);

        //validation
        if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication() == null){

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            var isTokenValid = tokenRepository.findByToken(jwt)
                    .map(t-> !t.isExpired() && !t.isRevoked())
                    .orElse(false);

            // check if the token is valid or not
            if(jwtService.isTokenValid(jwt,userDetails) && isTokenValid){

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                // Above: reason for using userDetails.getAuthorities instead of userDetails.getRole()
                // is because a user can have more than one role

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );


                SecurityContextHolder.getContext().setAuthentication(authToken);


            }
        }
        filterChain.doFilter(request,response);

    }
}
