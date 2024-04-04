package hr.card.management.api.controller;

import hr.card.management.api.controller.model.CardRequestCommand;
import hr.card.management.api.controller.model.CardRequestDto;
import hr.card.management.api.controller.service.CardRequestApiService;
import hr.card.management.api.domain.annotations.KafkaSender;
import hr.card.management.api.domain.annotations.ValidationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/new-card-request")
@RequiredArgsConstructor
public class CardRequestController {

    private final CardRequestApiService apiService;
    private static final String CARD_REQUEST_TOPIC = "card-request-topic";

    @GetMapping("/{id}")
    public CardRequestDto findNewCardRequestById(@PathVariable Long id) {
        return apiService.findCardRequestById(id);
    }

    @GetMapping("")
    public List<CardRequestDto> findAll() {
        return apiService.findAll();
    }

    @GetMapping("/oib/{oib}")
    public CardRequestDto findNewCardRequestsByOib(@PathVariable String oib) {
        return apiService.findCardRequestsByOib(oib);
    }

    @ValidationCheck
    @KafkaSender(topic = CARD_REQUEST_TOPIC)
    @PostMapping("")
    public CardRequestDto createNewCardRequest(@RequestBody CardRequestCommand cardRequest) {
        return apiService.createCardRequest(cardRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteNewCardRequest(@PathVariable Long id) {
        apiService.deleteCardRequest(id);
    }

}
