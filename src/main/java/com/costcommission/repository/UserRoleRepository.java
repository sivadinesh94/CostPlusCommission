package com.costcommission.repository;

import com.costcommission.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Serializable> {

    @Query("select u from UserRole u where u.userId=:userId")
    List<UserRole> findByUserId(@Param("userId") Long userId);
}
