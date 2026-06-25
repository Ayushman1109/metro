package com.ayushman.metro.repository;

import com.ayushman.metro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
