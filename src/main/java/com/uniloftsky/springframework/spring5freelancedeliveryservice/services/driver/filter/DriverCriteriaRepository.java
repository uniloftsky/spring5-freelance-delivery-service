package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.driver.filter;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.DriverMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.DriverDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Driver;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.type.TypeService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class DriverCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final DriverMapper driverMapper;
    private final TypeService typeService;

    public DriverCriteriaRepository(EntityManager entityManager, DriverMapper driverMapper, TypeService typeService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.driverMapper = driverMapper;
        this.typeService = typeService;
    }

    public Page<DriverDTO> findAllWithFilters(DriverPage driverPage,
                                              DriverSearchCriteria driverSearchCriteria) {
        CriteriaQuery<Driver> criteriaQuery = criteriaBuilder.createQuery(Driver.class);
        Root<Driver> driverRoot = criteriaQuery.from(Driver.class);
        fetchOtherTables(driverRoot);
        Predicate predicate = getPredicate(driverSearchCriteria, driverRoot);
        criteriaQuery.where(predicate);
        setOrder(driverPage, criteriaQuery, driverRoot);

        TypedQuery<Driver> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(driverPage.getPageNumber() * driverPage.getPageSize());
        typedQuery.setMaxResults(driverPage.getPageSize());

        Pageable pageable = getPageable(driverPage);

        long itemsCount = getDriversCount(predicate);

        return new PageImpl<>(typedQuery.getResultList().stream().map(driverMapper::driverToDriverDTO).collect(Collectors.toList()), pageable, itemsCount);
    }

    private Predicate getPredicate(DriverSearchCriteria driverSearchCriteria,
                                   Root<Driver> driverRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(driverSearchCriteria.getName())) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(driverRoot.get("name")),
                            "%" + driverSearchCriteria.getName().toLowerCase() + "%")
            );
        }
        if (Objects.nonNull(driverSearchCriteria.getMax_experience())) {
            if (driverRoot.get("experience") != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(driverRoot.get("experience"),
                        driverSearchCriteria.getMax_experience())
                );
            }
        }
        if (Objects.nonNull(driverSearchCriteria.getMin_experience())) {
            if (driverRoot.get("experience") != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(driverRoot.get("experience"),
                        driverSearchCriteria.getMin_experience())
                );
            }
        }
        if (Objects.nonNull(driverSearchCriteria.getTypes())) {
            for (String type : driverSearchCriteria.getTypes()) {
                predicates.add(
                        criteriaBuilder.isMember(typeService.findByName(type), driverRoot.get("types"))
                );
            }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(DriverPage driverPage,
                          CriteriaQuery<Driver> criteriaQuery,
                          Root<Driver> driverRoot) {
        if (driverPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(driverRoot.get(driverPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(driverRoot.get(driverPage.getSortBy())));
        }
    }

    private Pageable getPageable(DriverPage driverPage) {
        Sort sort = Sort.by(driverPage.getSortDirection(), driverPage.getSortBy());
        return PageRequest.of(driverPage.getPageNumber(), driverPage.getPageSize(), sort);
    }

    private long getDriversCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Driver> countRoot = countQuery.from(Driver.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private void fetchOtherTables(Root<Driver> driverRoot) {
        driverRoot.fetch("types", JoinType.LEFT);
        driverRoot.fetch("advertisements", JoinType.LEFT);
    }

}
