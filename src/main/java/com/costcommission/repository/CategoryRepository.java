package com.costcommission.repository;

import com.costcommission.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Serializable> {


}
