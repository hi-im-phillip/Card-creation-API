package hr.card.management.api.controller;

import hr.card.management.api.model.CardRequestCommand;
import hr.card.management.api.model.CardRequestCommandByOIB;
import hr.card.management.api.model.CardRequestDto;
import hr.card.management.api.model.ResponseBaseDto;
import hr.card.management.api.service.CardRequestApiService;
import hr.card.management.domain.annotations.KafkaSender;
import hr.card.management.domain.annotations.ValidationCheck;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/new-card-request")
@RequiredArgsConstructor
public class CardRequestController {

    private final CardRequestApiService apiService;
    private static final String CARD_REQUEST_TOPIC = "card-request-topic";

    @Operation(summary = "Find a new card request by ID")
    @GetMapping("/{id}")
    public CardRequestDto findNewCardRequestById(@PathVariable Long id) {
        return apiService.findCardRequestById(id);
    }

    @Operation(summary = "Get all card requests")
    @GetMapping("")
    public List<CardRequestDto> findAll() {
        return apiService.findAll();
    }

    @Operation(summary = "Find card requests by OIB")
    @PostMapping("/oib")
    public ResponseBaseDto findNewCardRequestsByOib(@RequestBody CardRequestCommandByOIB oib) {
        return apiService.findCardRequestsByOib(oib);
    }

    @Operation(summary = "Create a new card request")
    @ValidationCheck
    @KafkaSender(topic = CARD_REQUEST_TOPIC)
    @PostMapping("")
    public CardRequestDto createNewCardRequest(@RequestBody CardRequestCommand cardRequest) {
        return apiService.createCardRequest(cardRequest);
    }

    @Operation(summary = "Delete a new card request")
    @DeleteMapping("/{id}")
    public void deleteNewCardRequest(@PathVariable Long id) {
        apiService.deleteCardRequest(id);
    }

}
