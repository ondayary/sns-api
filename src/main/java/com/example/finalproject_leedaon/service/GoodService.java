package com.example.finalproject_leedaon.service;

import com.example.finalproject_leedaon.domain.entity.Good;
import com.example.finalproject_leedaon.domain.entity.Post;
import com.example.finalproject_leedaon.domain.entity.User;
import com.example.finalproject_leedaon.exception.AppException;
import com.example.finalproject_leedaon.repository.GoodRepository;
import com.example.finalproject_leedaon.repository.PostRepository;
import com.example.finalproject_leedaon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.finalproject_leedaon.exception.ErrorCode.POST_NOT_FOUND;
import static com.example.finalproject_leedaon.exception.ErrorCode.USERNAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GoodService {

    // 의존성 주입
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final GoodRepository goodRepository;

    // 좋아요 누르기
    public Integer goodPush(Integer postId, String userName) {

        // userName이 없는 경우
        User user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(USERNAME_NOT_FOUND, USERNAME_NOT_FOUND.getMessage()));

        // post가 없는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));

        Optional<Good> optionalGood = goodRepository.findByUserAndPost(user, post);

//        Optional<Good> optionalGood = goodRepository.findByUserAndPost(user, post)
//                .ifPresent(item-> new AppException(ALREADY_EXISTS, ALREADY_EXISTS.getMessage()));

        if(optionalGood.isPresent()) { // 좋아요가 존재하는 경우
            goodRepository.delete(optionalGood.get());
            return 0;
        } else { // 좋아요가 존재하지 않는 경우
            goodRepository.save(Good.goodPush(user, post));
            return 1;
        }
    }

    // 좋아요 개수
    public Integer goodCount(Integer postId) {

        // post가 없는 경우
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(POST_NOT_FOUND, POST_NOT_FOUND.getMessage()));

//        Integer goodCount = goodRepository.countByPost(post);
        return goodRepository.countByPost(post);
    }
}
