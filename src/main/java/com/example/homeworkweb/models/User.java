package com.example.homeworkweb.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String email;
    private String password;
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Blog> blogs;
}
