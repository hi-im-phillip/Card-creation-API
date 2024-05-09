package hr.card.management.web.ui.controller;

import hr.card.management.web.ui.model.CardRequestCommand;
import hr.card.management.web.ui.model.CardRequestDto;
import hr.card.management.web.ui.model.ResponseBaseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CardController {

    @Value("${card.management.api.url}")
    private String backendUrl;

    private final RestTemplate restTemplate;


    @GetMapping("/card-requests")
    public ModelAndView index() {
        ResponseEntity<CardRequestDto[]> responseEntity = restTemplate.getForEntity(backendUrl, CardRequestDto[].class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            CardRequestDto[] cardRequestArray = responseEntity.getBody();
            assert cardRequestArray != null;
            List<CardRequestDto> cardRequestList = Arrays.asList(cardRequestArray);
            return new ModelAndView("card-requests", "cardRequests", cardRequestList);
        } else
            throw new IllegalStateException(String.format("Unable to list card requests, received status %s", responseEntity.getStatusCode()));
    }

    @GetMapping("/find-by-oib")
    public ModelAndView findByOib(@RequestParam(required = false) String oib) {

        CardRequestCommand cardRequestCommandByOIB = new CardRequestCommand();
        cardRequestCommandByOIB.setOib(oib);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<CardRequestCommand> entity = new HttpEntity<>(cardRequestCommandByOIB, headers);

        ResponseEntity<ResponseBaseDto> response = restTemplate.exchange(backendUrl + "/oib", HttpMethod.POST, entity, ResponseBaseDto.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            if (response.getBody() == null || response.getBody().getItems() == null) {
                return new ModelAndView("find-by-oib");
            } else {
                List<CardRequestDto> cardRequestDtoList = (List<CardRequestDto>) Objects.requireNonNull(response.getBody().getItems());
                return new ModelAndView("find-by-oib", "cardRequests", cardRequestDtoList);
            }
        } else
            throw new IllegalStateException(String.format("Unable to list cardRequest, received status %s", response.getStatusCode()));

    }

    @GetMapping("/card-requests/create")
    public ModelAndView create() {
        return new ModelAndView("card-request-create", "cardRequest", new CardRequestCommand());
    }

    @PostMapping("/card-requests/create")
    public String create(@ModelAttribute CardRequestCommand cardRequest) {
        ResponseEntity<CardRequestCommand> responseEntity = restTemplate.postForEntity(backendUrl, cardRequest, CardRequestCommand.class);

        if (responseEntity.getStatusCode() != HttpStatus.OK)
            throw new IllegalStateException(String.format("Unable to create customer, received status %s", responseEntity.getStatusCode()));

        return "redirect:/card-requests";
    }

    @GetMapping("/card-requests/delete/{id}")
    public String delete(@PathVariable Integer id) {
        restTemplate.delete(backendUrl + "/{id}", id);

        return "redirect:/card-requests";
    }
}
