package hr.card.management.api.infrastructure.repository;

import hr.card.management.api.infrastructure.model.CardRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NewCardRequestRepository extends CrudRepository<CardRequest, Long> {

    Optional<CardRequest> findNewCardRequestByOib(String oib);
}
