package com.adminpaint.repository;

import com.adminpaint.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsernameAndPassword(String username, String password);

    Optional<Users> findFirstByUsername(String username);

}
