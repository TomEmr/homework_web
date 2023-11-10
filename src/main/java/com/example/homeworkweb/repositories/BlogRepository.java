package com.example.homeworkweb.repositories;

import com.example.homeworkweb.models.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    @Query("SELECT b FROM Blog b WHERE b.title LIKE %?1% OR b.content LIKE %?1%")
    Page<Blog> findByTitleContainingOrContentContaining(String keyword, Pageable pageable);
}
