package com.example.finalproject_leedaon.repository;

import com.example.finalproject_leedaon.domain.entity.Good;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Integer> {
    Optional<Good> findByUserAndPost(User user, Post post);
}
