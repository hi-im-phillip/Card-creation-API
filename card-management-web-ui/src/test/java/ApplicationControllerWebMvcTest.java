import hr.card.management.web.ui.configuration.RestTemplateConfiguration;
import hr.card.management.web.ui.model.CardRequestCommand;
import hr.card.management.web.ui.model.CardRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This class tests the CardController class
 * Order to work properly, the backend server must be running
 */
@SpringBootTest
@ContextConfiguration(classes = {RestTemplateConfiguration.class})
class CardControllerTest {


    @Autowired
    private RestTemplate restTemplate;

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
    void testFindByOibResponseStatus_OK() {
        CardRequestDto cardRequestDto = new CardRequestDto(5001L, "John", "Wayne", "PENDING", "87745921050");
        ResponseEntity<CardRequestDto> responseEntity = new ResponseEntity<>(cardRequestDto, HttpStatus.OK);

        ResponseEntity<CardRequestDto> response = restTemplate.getForEntity(BACKEND_URL + "/oib/" + cardRequestDto.getOib(), CardRequestDto.class);

        assertEquals(responseEntity.getStatusCode(), response.getStatusCode());
        assertEquals(responseEntity.getBody(), response.getBody());
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