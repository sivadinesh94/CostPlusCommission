package com.costcommission.jpaspecification;

import com.costcommission.dto.SpecificationDTO;
import com.costcommission.entity.Region;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class RegionSpecification implements Specification<Region> {

    SpecificationDTO criteria;

    public RegionSpecification() {

    }

    public RegionSpecification(final SpecificationDTO specificationDTO) {
        super();
        this.criteria = specificationDTO;
    }

    @Override
    public Predicate toPredicate
            (Root<Region> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

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