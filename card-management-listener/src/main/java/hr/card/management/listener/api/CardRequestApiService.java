package hr.card.management.listener.api;

import hr.card.management.listener.api.service.ApiService;
import hr.card.management.listener.domain.model.CardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Component
public class CardRequestApiService implements ApiService<CardRequestDto, Long> {

    private final RestTemplate restTemplate;

    @Value("${card.management.api.url}")
    private String backendUrl;

    @Override
    public CardRequestDto findById(Long id) {
        ResponseEntity<CardRequestDto> responseEntity = restTemplate.getForEntity(backendUrl + "/{id}", CardRequestDto.class, id);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }
        return null;
    }

    @Override
    public CardRequestDto save(CardRequestDto dto) {
        ResponseEntity<CardRequestDto> responseEntity = restTemplate.postForEntity(backendUrl, dto, CardRequestDto.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK && responseEntity.getBody() != null) {
            return responseEntity.getBody();
        }
        return null;
    }
}
