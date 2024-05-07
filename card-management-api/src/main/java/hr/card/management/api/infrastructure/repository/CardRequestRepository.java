package hr.card.management.api.infrastructure.repository;

import hr.card.management.api.infrastructure.model.CardRequest;
import org.springframework.data.repository.CrudRepository;

public interface CardRequestRepository extends CrudRepository<CardRequest, Long>, CardRequestSearchRepository {

}
