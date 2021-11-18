package com.costcommission.repository;

import com.costcommission.entity.COA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.Serializable;

@Repository
@Transactional
public interface COARepository extends JpaRepository<COA, Serializable> {

    @Query(value = "SELECT count(id) FROM COA where natureCode = :natureCode")
    Long count(@Param("natureCode")String natureCode);

    COA  findByNatureCode(String code);

    @Modifying
    @Query("update COA  set isActive = :status where id = :id")
    int enableOrDisable(@Param("status")boolean status, @Param("id")Long id);

    @Modifying
    @Query("update COA  set natureDesc = :natureDesc,description = :description,safranpl = :safranpl,safranplDetails = :safranplDetails," +
            "agencyBudgetCode = :agencyBudgetCode,agencyBudgetLine = :agencyBudgetLine,mandatoryAllocPerCC = :mandatoryAllocPerCC,unallocatedExpenses = :unallocatedExpenses," +
            "costCenterAllocation = :costCenterAllocation,natureNotRelevant = :natureNotRelevant,rationale = :rationale,costPlusMatrix = :costPlusMatrix," +
            "descriptionEnglish = :descriptionEnglish,status = :status where natureCode = :natureCode")
    Integer update(@Param("natureDesc") String natureDesc, @Param("description") String description, @Param("safranpl") String safranpl,
               @Param("safranplDetails") String safranplDetails, @Param("agencyBudgetCode") String agencyBudgetCode, @Param("agencyBudgetLine") String agencyBudgetLine,
               @Param("mandatoryAllocPerCC") String mandatoryAllocPerCC, @Param("unallocatedExpenses") String unallocatedExpenses, @Param("costCenterAllocation") String costCenterAllocation,
               @Param("natureNotRelevant") String natureNotRelevant, @Param("rationale") String rationale, @Param("costPlusMatrix") String costPlusMatrix,
               @Param("descriptionEnglish") String descriptionEnglish, @Param("status") boolean status,@Param("natureCode")  String natureCode);
}
