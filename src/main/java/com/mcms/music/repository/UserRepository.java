package com.mcms.music.repository;

import com.mcms.music.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
