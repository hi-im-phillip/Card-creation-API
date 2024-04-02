package hr.card.management.infrastructure.repository;

import hr.card.management.infrastructure.model.NewCardRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface NewCardRequestRepository extends CrudRepository<NewCardRequest, Long> {

    Optional<NewCardRequest> findNewCardRequestByOib(String oib);
}
