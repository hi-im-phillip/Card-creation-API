package hr.card.management.listener.domain.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.card.management.listener.api.service.ApiService;
import hr.card.management.listener.domain.model.CardRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardRequestStatusListener {

    private final ApiService<CardRequestDto, Long> apiService;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "card-request-topic", groupId = "myGroup")
    public void listenCardRequestStatus(String message) {
        try {
            CardRequestDto cardRequest = objectMapper.readValue(message, CardRequestDto.class);
            CardRequestDto existingCardRequest = apiService.findById(cardRequest.getId());

            if (existingCardRequest != null) {
                existingCardRequest.setStatus("PROCESSED");
                CardRequestDto updatedCardRequest = apiService.save(existingCardRequest);
                kafkaTemplate.send("card-request-processed-topic", "Card request with ID " + updatedCardRequest.getId() + " processed successfully");
            } else {
                log.warn("Card request with ID " + cardRequest.getId() + " not found");
            }
        } catch (IOException e) {
            log.error("Error deserializing JSON: " + e.getMessage());
        }
    }
}
