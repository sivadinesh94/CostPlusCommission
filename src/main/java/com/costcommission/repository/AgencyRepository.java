package com.costcommission.repository;

import com.costcommission.entity.Agency;
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
public interface AgencyRepository extends JpaRepository<Agency, Serializable>, JpaSpecificationExecutor<Agency> {

    @Modifying
    @Query("update Agency a set a.isActive = :status where a.id = :id")
    int inActivate(@Param("status") boolean status,@Param("id") Long id);

    @Query(value = "SELECT count(id) FROM Agency where code = :code")
    Long countCode(@Param("code") String code);

    @Query(value = "SELECT count(id) FROM Agency where name=:name")
    Long countName(@Param("name") String name);

    Agency findAgencyByName(String name);

    Agency findByCode(String code);

    @Modifying
    @Query("update Agency  set description = :description , name= :name, safEntityNumber=:seNo," +
            " legalEntityName=:leName,oceanType=:oType,markup=:markup,headCountFlag=:hcFlag,fcCcy=:fc,location=:location, " +
            " templateName=:tName , cluster_id=:clusterId where id = :id")
    Integer update(@Param("description") String description, @Param("name") String name, @Param("clusterId") Long clusterId,
                   @Param("seNo") Long seNo, @Param("leName") String leName, @Param("oType") String oType, @Param("markup") Integer markup,
                   @Param("hcFlag") Boolean hcFlag, @Param("fc") String fc, @Param("location") String location, @Param("tName") String tName,
                   @Param("id") Long id);

    Agency findByNameOrCode(String name, String code);

    Agency findAgencyById(Long id);

    @Query(value = "SELECT count(a.id) FROM Agency a where a.code = :code and a.id not in :id")
    public Long countByIdAndCode(@Param("code") String code, @Param("id") Long id);

    @Query(value = "SELECT count(a.id) FROM Agency a where a.name = :name and a.id not in :id")
    public Long countByIdAndName(@Param("name") String name, @Param("id") Long id);

    @Query("select a from Agency a where a.deleted = false")
    List<Agency> allActive();

    @Query("select a from Agency a where a.deleted=false")
    List<Agency> getActiveAndInactiveWithPagination(Specification<Agency> spec, Pageable pageable);

    @Query("select count(a.id) from Agency a where a.deleted=false")
    int countActiveAndInactive();
}
