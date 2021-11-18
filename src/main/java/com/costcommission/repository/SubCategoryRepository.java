package com.costcommission.repository;

import com.costcommission.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Serializable> {

    @Query(value = "Select s from SubCategory s where s.category.id=:categoryId and s.deleted=false")
    List<SubCategory> findByCategoryId(@Param("categoryId") Long categoryId);

    @Query(value = "Select s from SubCategory s where s.id=:id and s.deleted=false")
    SubCategory findById(@Param("id") Long id);


}
