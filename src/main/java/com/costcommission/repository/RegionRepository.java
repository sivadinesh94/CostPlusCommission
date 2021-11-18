package com.costcommission.repository;

import com.costcommission.entity.Region;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Repository
@Transactional
public interface RegionRepository extends JpaRepository<Region, Serializable>, JpaSpecificationExecutor<Region> {

    @Query(value = "SELECT count(id) FROM Region where lower(code) = :code")
    public Long countByCode(@Param("code") String code);

    @Query(value = "SELECT count(id) FROM Region where lower(name) = :name")
    public Long countByName(@Param("name") String name);

    @Query(value = "SELECT count(id) FROM Region r where lower(r.code) = :code and r.id not in :id")
    public Long countByIdAndCode(@Param("code") String code, @Param("id") Long id);

    @Query(value = "SELECT count(id) FROM Region r where lower(r.code) = :name and r.id not in :id")
    public Long countByIdAndName(@Param("name") String name, @Param("id") Long id);

    @Modifying
    @Query("update Region set name= :name where id = :id")
    Integer update(@Param("name") String name, @Param("id") Long id);

    Region findByCode(String code);

    Region findByNameOrCode(String name,String code);

    @Query("select r from Region r where r.id=:id and r.deleted=false")
    Region findRegionById(@Param("id") Long id);

    @Modifying
    @Query("update Region r set r.isActive = :deleted where r.id = :id")
    int enableOrDisable(@Param("deleted") boolean deleted,@Param("id") Long id);

    @Query("select r from Region r where r.deleted = false")
    List<Region> allActive();

    Region findByName(String name);

    @Query("select r from Region r where r.deleted=false")
    List<Region> getActiveAndInactiveWithPagination(Specification<Region> spec, Pageable pageable);

    List<Region> findByDeletedFalse(Specification<Region> spec, Pageable pageable);

    @Query("select count(r.id) from Region r where r.deleted=false")
    int getActiveAndInactive();

    List<Region> findRegionByIsActive(boolean b);
}
