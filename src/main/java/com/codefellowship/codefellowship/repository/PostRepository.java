package com.codefellowship.codefellowship.repository;

import com.codefellowship.codefellowship.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserUsername(String username);
}
