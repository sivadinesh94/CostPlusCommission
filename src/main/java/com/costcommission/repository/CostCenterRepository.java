package com.costcommission.repository;

import com.costcommission.entity.Agency;
import com.costcommission.entity.CostCenter;
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
public interface CostCenterRepository extends JpaRepository<CostCenter, Serializable>, JpaSpecificationExecutor<CostCenter> {

    @Query("select c from CostCenter c where c.deleted=false")
    List<CostCenter> findAllCostCenter();

    @Query(value = "SELECT count(id) FROM CostCenter where code = :code or name=:name")
    Long count(@Param("code") String code, @Param("name")String name);

    @Modifying
    @Query("update CostCenter  set name= :costCenterName,sequence=:sequence,keyAllocation=:keyAllocation,status=:status,carrierCode=:carrierCode,carrierName=:carrierName where id=:id")
    Integer update(@Param("costCenterName") String costCenterName, @Param("sequence")Integer sequence, @Param("keyAllocation")String keyAllocation,
                   @Param("status")Boolean status, @Param("carrierCode")String carrierCode, @Param("carrierName")String carrierName, @Param("id")Long id);

    CostCenter findByNameOrCode(String name, String code);

    CostCenter findByName(String name);

    CostCenter findByCode(String code);

    @Modifying
    @Query("update CostCenter  set isActive = :status where id = :id")
    int enableOrDisable(@Param("status") boolean status,@Param("id") Long id);

    List<CostCenter> findCostCenterByType(String type);

    @Query("select c from CostCenter c where c.deleted=false")
    List<CostCenter> getActiveAndInactiveWithPagination(Specification<CostCenter> spec, Pageable pageable);

    @Query("select count(c.id) from CostCenter c where c.deleted=false")
    int countActiveAndInactive();
}
