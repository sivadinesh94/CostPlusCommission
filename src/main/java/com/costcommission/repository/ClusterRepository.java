package com.costcommission.repository;

import com.costcommission.entity.Cluster;
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
public interface ClusterRepository extends JpaRepository<Cluster, Serializable>, JpaSpecificationExecutor<Cluster> {


    @Query(value = "SELECT count(id) FROM Cluster where code = :code")
    Long countByCode(@Param("code") String code );

    @Query(value = "SELECT count(id) FROM Cluster where name = :name")
    Long countByName(@Param("name") String name );

    @Query(value = "SELECT count(id) FROM Cluster c where c.code = :code and c.id not in :id")
    public Long countByIdAndCode(@Param("code") String code, @Param("id") Long id);

    @Query(value = "SELECT count(id) FROM Cluster c where c.name = :name and c.id not in :id")
    public Long countByIdAndName(@Param("name") String name, @Param("id") Long id);

    @Modifying
    @Query("update Cluster  set  name= :name,region_id=:region_id where id = :id")
    Integer update(@Param("name") String name, @Param("region_id") Long region_id, @Param("id") Long id);

    @Modifying
    @Query("update Cluster c set c.isActive = :status where c.id = :id")
    int enableOrDisable(@Param("status") boolean status,@Param("id") Long id);

    Cluster findClusterByNameOrCode(String name,String code);

    Cluster findByCode(String code);

    @Query("select c from Cluster c where c.id=:id and c.deleted=false")
    Cluster findById(@Param("id") Long id);

    Cluster findClusterByName(String name);

    @Query("select c from Cluster c where c.deleted = false")
    List<Cluster> allActive();

    @Query("select c from Cluster c where c.deleted=false")
    List<Cluster> getActiveAndInactiveWithPagination(Specification<Cluster> spec, Pageable pageable);

    @Query("select count(c.id) from Cluster c where c.deleted=false")
    int countActiveAndInactive();

    List<Cluster> findClusterByIsActive(boolean isActive);
}
