package com.adminpaint.repository;

import com.adminpaint.model.Commissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommissionsRepository extends JpaRepository<Commissions, Integer> {

    Long countById(Integer id);

}
