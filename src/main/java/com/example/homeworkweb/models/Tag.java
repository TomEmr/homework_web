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
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Blog> blogs;
}
