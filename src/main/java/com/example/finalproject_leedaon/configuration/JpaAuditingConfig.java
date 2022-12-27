package com.example.finalproject_leedaon.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // Audit기능을 쓰겠다고 선언한다.
public class JpaAuditingConfig {

    // 이 클래스를 만든 이후에 BaseEntity를 만든다.
    // 그 BaseEntity를 Entity에 extends하면 Audit이 적용 된다.
}