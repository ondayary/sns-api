package com.example.finalproject_leedaon.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User extends UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true) // 중복체크를 위해서 걸어준다.
    private String userName;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

/*    @Builder
    public User(Integer id, String userName, String password, UserRole role) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
    }*/
}
