package com.uniloftsky.springframework.spring5freelancedeliveryservice.services.advertisement.filter;

import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.mappers.AdvertisementMapper;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.api.v1.model.AdvertisementDTO;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.model.Advertisement;
import com.uniloftsky.springframework.spring5freelancedeliveryservice.services.type.TypeService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class AdvertisementCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;
    private final AdvertisementMapper advertisementMapper;
    private final TypeService typeService;

    public AdvertisementCriteriaRepository(EntityManager entityManager, AdvertisementMapper advertisementMapper, TypeService typeService) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
        this.advertisementMapper = advertisementMapper;
        this.typeService = typeService;
    }

    public Page<AdvertisementDTO> findAllWithFilters(AdvertisementPage advertisementPage,
                                                     AdvertisementSearchCriteria advertisementSearchCriteria) {
        CriteriaQuery<Advertisement> criteriaQuery = criteriaBuilder.createQuery(Advertisement.class);
        Root<Advertisement> advertisementRoot = criteriaQuery.from(Advertisement.class);
        Predicate predicate = getPredicate(advertisementSearchCriteria, advertisementRoot);
        criteriaQuery.where(predicate);
        setOrder(advertisementPage, criteriaQuery, advertisementRoot);

        TypedQuery<Advertisement> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(advertisementPage.getPageNumber() * advertisementPage.getPageSize());
        typedQuery.setMaxResults(advertisementPage.getPageSize());

        Pageable pageable = getPageable(advertisementPage);

        long itemsCount = getAdvertisementsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList().stream().map(advertisementMapper::advertisementToAdvertisementDTO).collect(Collectors.toList()), pageable, itemsCount);
    }

    private Predicate getPredicate(AdvertisementSearchCriteria advertisementSearchCriteria,
                                   Root<Advertisement> advertisementRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if (Objects.nonNull(advertisementSearchCriteria.getTitle())) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(advertisementRoot.get("title")),
                            "%" + advertisementSearchCriteria.getTitle().toLowerCase() + "%")
            );
        }
        if (Objects.nonNull(advertisementSearchCriteria.getDeliver_from())) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(advertisementRoot.get("deliverFrom")),
                            "%" + advertisementSearchCriteria.getDeliver_from().toLowerCase() + "%")
            );
        }
        if (Objects.nonNull(advertisementSearchCriteria.getDeliver_to())) {
            predicates.add(
                    criteriaBuilder.like(criteriaBuilder.lower(advertisementRoot.get("deliverTo")),
                            "%" + advertisementSearchCriteria.getDeliver_to().toLowerCase() + "%")
            );
        }
        if (Objects.nonNull(advertisementSearchCriteria.getMax_price())) {
            if (advertisementRoot.get("price") != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(advertisementRoot.get("price"),
                        advertisementSearchCriteria.getMax_price())
                );
            }
        }
        if (Objects.nonNull(advertisementSearchCriteria.getMin_price())) {
            if (advertisementRoot.get("price") != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(advertisementRoot.get("price"),
                        advertisementSearchCriteria.getMin_price())
                );
            }
        }
        if (Objects.nonNull(advertisementSearchCriteria.getTypes())) {
            for (String type : advertisementSearchCriteria.getTypes()) {
                predicates.add(
                        criteriaBuilder.isMember(typeService.findByName(type), advertisementRoot.get("types"))
                );
            }
        }
        if (Objects.nonNull(advertisementSearchCriteria.getStatus())) {
            predicates.add(criteriaBuilder.equal(
                    advertisementRoot.get("status"), advertisementSearchCriteria.getStatus())
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(AdvertisementPage advertisementPage,
                          CriteriaQuery<Advertisement> criteriaQuery,
                          Root<Advertisement> advertisementRoot) {
        if (advertisementPage.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(advertisementRoot.get(advertisementPage.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(advertisementRoot.get(advertisementPage.getSortBy())));
        }
    }

    private Pageable getPageable(AdvertisementPage advertisementPage) {
        Sort sort = Sort.by(advertisementPage.getSortDirection(), advertisementPage.getSortBy());
        return PageRequest.of(advertisementPage.getPageNumber(), advertisementPage.getPageSize(), sort);
    }

    private long getAdvertisementsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Advertisement> countRoot = countQuery.from(Advertisement.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

}
