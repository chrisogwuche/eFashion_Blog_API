package com.decagon.fashionBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NonNull
@NoArgsConstructor

public class LoginDTO {
    private String email;
    private String password;
}
