package com.decagon.fashionBlog.dto;

import lombok.Data;
import lombok.NonNull;

@NonNull
@Data
public class PostsDTO {
    private String title;
    private String description;
    private String price;
}
