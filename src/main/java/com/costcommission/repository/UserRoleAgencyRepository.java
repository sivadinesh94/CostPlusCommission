package com.costcommission.repository;

import com.costcommission.entity.UserRoleAgency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserRoleAgencyRepository extends JpaRepository<UserRoleAgency, Serializable> {


}
