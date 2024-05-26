package com.training.security.authorizationserver.repository;

import com.training.security.authorizationserver.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {

    Privilege findByName(String name);

    Privilege save(Privilege privilege);
}