package com.mechurao.taskmanagementsystem.implementation.jpa.repository;

import com.mechurao.taskmanagementsystem.implementation.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
}
