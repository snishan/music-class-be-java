package com.mcms.music.repository;

import com.mcms.music.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
}
