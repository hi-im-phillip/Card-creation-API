package hr.card.management.api.infrastructure.repository;

import hr.card.management.api.infrastructure.model.CardRequest;
import hr.card.management.api.infrastructure.model.criteria.SearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardRequestSearchRepository {

    Page<CardRequest> findEntitiesBySearchCriteria(SearchCriteria searchCriteria, Pageable pageable);
}