package hr.card.management.infrastructure.repository;

import hr.card.management.infrastructure.model.CardRequest;
import org.springframework.data.repository.CrudRepository;

public interface CardRequestRepository extends CrudRepository<CardRequest, Long>, CardRequestSearchRepository {

}
