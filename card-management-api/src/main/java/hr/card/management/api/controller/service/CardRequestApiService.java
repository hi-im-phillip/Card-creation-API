package hr.card.management.api.controller.service;

import hr.card.management.api.controller.model.CardRequestCommand;
import hr.card.management.api.controller.model.CardRequestDto;

import java.util.List;

public interface CardRequestApiService {

    CardRequestDto createCardRequest(CardRequestCommand command);

    CardRequestDto findCardRequestById(Long id);

    List<CardRequestDto> findAll();

    CardRequestDto findCardRequestsByOib(String oib);

    void deleteCardRequest(Long id);
}
