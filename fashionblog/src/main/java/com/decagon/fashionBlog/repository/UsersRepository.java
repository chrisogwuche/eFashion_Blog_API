package com.decagon.fashionBlog.repository;

import com.decagon.fashionBlog.entity.Users;
import com.decagon.fashionBlog.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByEmail(String email);

}
