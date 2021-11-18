package com.costcommission.repository;

import com.costcommission.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
@Repository
public interface DesignationRepository extends JpaRepository<Designation, Serializable> {

    Designation findDesignationById(Long id);
}
