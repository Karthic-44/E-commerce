package com.karthic.ecommerce.repositories;

import com.karthic.ecommerce.model.AppRole;

import com.karthic.ecommerce.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {


   Optional<Role> findByRoleName(AppRole appRole);
}
