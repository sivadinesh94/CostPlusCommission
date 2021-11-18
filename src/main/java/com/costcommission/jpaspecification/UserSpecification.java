package com.costcommission.jpaspecification;

import com.costcommission.dto.SpecificationDTO;
import com.costcommission.entity.Region;
import com.costcommission.entity.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSpecification implements Specification<User> {

    SpecificationDTO criteria;

    public UserSpecification() {

    }

    public UserSpecification(final SpecificationDTO specificationDTO) {
        super();
        this.criteria = specificationDTO;
    }

    @Override
    public Predicate toPredicate
            (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase("EQUAL_TO")) {
            return builder.equal(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("NOT_EQUAL_TO")) {
            return builder.notEqual(
                    root.<String> get(criteria.getKey()), criteria.getValue().toString());
        }
        else if (criteria.getOperation().equalsIgnoreCase("CONTAINS")) {
            return builder.like(
                    root.<String> get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
        }
        else if (criteria.getOperation().equalsIgnoreCase("NOT_CONTAINS")) {
            return builder.notLike(
                    root.<String> get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
        }
        else if (criteria.getOperation().equalsIgnoreCase("STARTS_WITH")) {
            return builder.like(
                    root.<String> get(criteria.getKey()),   criteria.getValue().toString() + "%");
        }
        else if (criteria.getOperation().equalsIgnoreCase("ENDS_WITH")) {
            return builder.like(
                    root.<String> get(criteria.getKey()), "%" + criteria.getValue().toString());
        }
        return null;
    }
}
