package hr.card.management.infrastructure.repository;

import hr.card.management.infrastructure.model.CardRequest;
import hr.card.management.infrastructure.model.criteria.SearchCriteria;
import hr.card.management.infrastructure.model.exception.IllegalSearchCriteriaAccessException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CardRequestSearchRepositoryImpl implements CardRequestSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private final Set<String> cardRequestFields;

    public CardRequestSearchRepositoryImpl() {
        this.cardRequestFields = new HashSet<>();
        for (Field field : CardRequest.class.getDeclaredFields()) {
            cardRequestFields.add(field.getName());
        }
    }

    @Override
    public Page<CardRequest> findEntitiesBySearchCriteria(SearchCriteria searchCriteria, Pageable pageable) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<CardRequest> cq = cb.createQuery(CardRequest.class);

        Root<CardRequest> root = cq.from(CardRequest.class);
        for (Field field : searchCriteria.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!cardRequestFields.contains(field.getName())) {
                throw new IllegalArgumentException("Field " + field.getName() + " does not exist in CardRequest");
            }
            try {
                Object value = field.get(searchCriteria);
                if (value != null) {
                    cq.where(cb.equal(root.get(field.getName()), value));
                }
            } catch (IllegalAccessException e) {
                throw new IllegalSearchCriteriaAccessException("Failed to access field value in search criteria", e);
            }
        }
        pageable.getSort().forEach(order -> {
            if (order.isAscending()) {
                cq.orderBy(cb.asc(root.get(order.getProperty())));
            } else {
                cq.orderBy(cb.desc(root.get(order.getProperty())));
            }
        });

        TypedQuery<CardRequest> query = entityManager.createQuery(cq);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<CardRequest> content = query.getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(CardRequest.class)));
        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(content, pageable, total);
    }
}