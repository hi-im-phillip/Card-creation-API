package hr.card.management.web.ui.controller;

import hr.card.management.web.ui.ControllerTest;
import hr.card.management.web.ui.model.CardRequestCommand;
import hr.card.management.web.ui.model.CardRequestDto;
import hr.card.management.web.ui.model.ResponseBaseDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the CardController class
 * Order to work properly, the backend server must be running
 */
class CardControllerTest extends ControllerTest {


    @Value("${card.management.api.url}")
    private String BACKEND_URL;


    @Test
    void testAllCardRequestResponseStatus_OK() {
        // Mock the response from restTemplate
        CardRequestDto[] cardRequestDto = {
                new CardRequestDto(5001L, "John", "Wayne", "PENDING", "87745921050"),
                new CardRequestDto(5002L, "Michael", "Scarn", "IN_PROGRESS", "HR97137616526"),
                new CardRequestDto(5003L, "Frank", "Gallagher", "APPROVED", "61158743615")
        };

        ResponseEntity<CardRequestDto[]> responseEntity = new ResponseEntity<>(cardRequestDto, HttpStatus.OK);
        ResponseEntity<CardRequestDto[]> response = restTemplate.getForEntity(BACKEND_URL, CardRequestDto[].class);

        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
    }

    @Test
    void testFindByOibResponseStatus_OK() throws JSONException {
        JSONObject cardRequestCommandByOIB = new JSONObject();
        cardRequestCommandByOIB.put("oib", "12345678901");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(cardRequestCommandByOIB.toString(), headers);

        ResponseEntity<ResponseBaseDto> response = restTemplate.exchange(BACKEND_URL + "/oib", HttpMethod.POST, entity, ResponseBaseDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateCardRequestResponseStatus_OK() {
        // Mock the response from restTemplate
        CardRequestCommand cardRequestCommand = new CardRequestCommand(null, "Michael", "Scott", "IN_PROGRESS", "HR25137623526");
        ResponseEntity<CardRequestCommand> responseEntity = new ResponseEntity<>(cardRequestCommand, HttpStatus.OK);

        ResponseEntity<CardRequestDto> response = restTemplate.postForEntity(BACKEND_URL, cardRequestCommand, CardRequestDto.class);

        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
        assertNotNull(response.getBody().getId());

        restTemplate.delete(BACKEND_URL + "/{id}", response.getBody().getId());
    }

    @Test
    void testDeleteCardRequestIsNull_OK() {

        CardRequestCommand cardRequestCommand = new CardRequestCommand(null, "Michael", "Scott", "IN_PROGRESS", "HR25137623526");

        ResponseEntity<CardRequestDto> response = restTemplate.postForEntity(BACKEND_URL, cardRequestCommand, CardRequestDto.class);

        // Verify that restTemplate.delete method is called with the correct URL
        restTemplate.delete(BACKEND_URL + "/{id}", response.getBody().getId());

        assertNull(restTemplate.getForEntity(BACKEND_URL + "/{id}", CardRequestDto.class, response.getBody().getId()).getBody());
    }
    
}
