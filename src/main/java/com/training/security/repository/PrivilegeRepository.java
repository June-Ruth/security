package com.training.security.repository;

import com.training.security.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    Privilege findByName(String name);

    Privilege save(Privilege privilege);
}