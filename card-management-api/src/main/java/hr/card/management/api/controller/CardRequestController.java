package hr.card.management.api.controller;

import hr.card.management.api.domain.annotations.KafkaSender;
import hr.card.management.api.domain.annotations.ValidationCheck;
import hr.card.management.api.infrastructure.model.CardRequest;
import hr.card.management.api.infrastructure.repository.NewCardRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/new-card-request")
@RequiredArgsConstructor
public class CardRequestController {

    private final NewCardRequestRepository newCardRequestRepository;


    private static final String CARD_REQUEST_TOPIC = "card-request-topic";
    
    @GetMapping("/{id}")
    public CardRequest findNewCardRequestById(@PathVariable Long id) {
        return newCardRequestRepository.findById(id).orElse(null);
    }

    @GetMapping("")
    public List<CardRequest> findAll() {
        return (List<CardRequest>) newCardRequestRepository.findAll();
    }

    @GetMapping("/oib/{oib}")
    public CardRequest findNewCardRequestsByOib(@PathVariable String oib) {
        return newCardRequestRepository.findNewCardRequestByOib(oib).orElse(null);
    }

    @ValidationCheck
    @KafkaSender(topic = CARD_REQUEST_TOPIC)
    @PostMapping("")
    public CardRequest createNewCardRequest(@RequestBody CardRequest cardRequest) {
        return newCardRequestRepository.save(cardRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteNewCardRequest(@PathVariable Long id) {
        newCardRequestRepository.deleteById(id);
    }

}
