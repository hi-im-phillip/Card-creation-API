package hr.card.management.controller;

import hr.card.management.domain.annotations.ValidationCheck;
import hr.card.management.infrastructure.model.NewCardRequest;
import hr.card.management.infrastructure.repository.NewCardRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/new-card-request")
@RequiredArgsConstructor
public class NewCardRequestController {

    private final NewCardRequestRepository newCardRequestRepository;

    @GetMapping("")
    public NewCardRequest findNewCardRequestsByOib(@RequestParam String oib) {
        return newCardRequestRepository.findNewCardRequestByOib(oib).orElse(null);
    }

    @ValidationCheck
    @PostMapping("")
    public NewCardRequest createNewCardRequest(@RequestBody NewCardRequest newCardRequest) {
        return newCardRequestRepository.save(newCardRequest);
    }

}
