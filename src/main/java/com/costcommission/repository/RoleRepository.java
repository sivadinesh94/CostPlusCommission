package com.costcommission.repository;

import com.costcommission.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface RoleRepository extends JpaRepository<Role, Serializable> {
    Role findRoleById(Long id);
}
