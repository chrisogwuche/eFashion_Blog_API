package com.decagon.fashionBlog.serviceImpl;

import com.decagon.fashionBlog.dto.UsersDTO;
import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.enums.Status;
import com.decagon.fashionBlog.exceptions.UsersNotFoundExceptions;
import com.decagon.fashionBlog.repository.UsersRepository;
import com.decagon.fashionBlog.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UsersRepository repo;


    @Override
    public List<Users> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Users deleteVisitor(Long visitorId) {
        Users visitor = authUser(visitorId);
        //check if the visitor is active or inactive
        if(visitor.getStatus() == Status.ACTIVE){
            visitor.setStatus(Status.INACTIVE);
            repo.save(visitor);
        }
        // return user with that id
        return authUser(visitorId);
    }

    @Override
    public Users logoutUser(Long id){
        Users user = authUser(id);
        repo.save(user);

        return authUser(user.getId());
    }

    public Users authUser(Long userId){
        return repo.findById(userId)
                .orElseThrow(()->
                        new UsersNotFoundExceptions("No user found with id: "+userId));
    }

    public Users getUserByEmail(String email){
        return repo.findByEmail(email)
                .orElseThrow(()->
                        new UsersNotFoundExceptions("No user found with email: " +email));
    }

}
