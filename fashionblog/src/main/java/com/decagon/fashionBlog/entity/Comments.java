package com.decagon.fashionBlog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="comments")
public class Comments {
    @Id
    @SequenceGenerator(
            name = "comments_sequence",
            sequenceName = "comments_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comments_sequence"
    )
    private Long id;
    @Column(nullable = false)
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @JsonIgnoreProperties("commentList")
    private Posts post;

}

