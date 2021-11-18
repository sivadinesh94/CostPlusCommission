package com.costcommission.repository;

import com.costcommission.entity.Agency;
import com.costcommission.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable> , JpaSpecificationExecutor<User> {

    @Query(value = "SELECT count(id) FROM User where loginId = :loginId or fullName=:name")
    Long countUser(@Param("loginId") String loginId, @Param("name") String name);

    User findUserByLoginIdAndPassword(String loginId, String Password);

    @Query("select u from User u where u.deleted=false")
    List<User> getActiveAndInactiveWithPagination(Specification<User> spec, Pageable pageable);

    @Query("select count(u.id) from User u where u.deleted=false")
    int countActiveAndInactive();

}
