package com.costcommission.repository;

import com.costcommission.entity.Cluster;
import com.costcommission.entity.PeriodControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface PeriodControlRepository extends JpaRepository<PeriodControl, Serializable> {
}
