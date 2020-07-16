package com.application.bankApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.bankApp.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByName(String roleName);
}
