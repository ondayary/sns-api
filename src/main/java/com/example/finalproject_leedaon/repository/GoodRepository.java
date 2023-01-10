package com.example.finalproject_leedaon.repository;

import com.example.finalproject_leedaon.domain.entity.Good;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Integer> {

    // 좋아요 누르기 기능
    Optional<Good> findByUserAndPost(User user, Post post);

    // 좋아요 개수 기능
    Integer countByPost(Post post);
}
