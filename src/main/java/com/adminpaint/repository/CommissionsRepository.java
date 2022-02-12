package com.adminpaint.repository;

import com.adminpaint.model.Commissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommissionsRepository extends JpaRepository<Commissions, Integer> {

    @Override
    Optional<Commissions> findById(Integer integer);
}
