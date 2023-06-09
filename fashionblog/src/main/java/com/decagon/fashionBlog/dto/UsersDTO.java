package com.decagon.fashionBlog.dto;

import com.decagon.fashionBlog.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class UsersDTO {
    private String firstname;
    private String lastname;
    private Role role;
    private String email;
    private String password;
}
