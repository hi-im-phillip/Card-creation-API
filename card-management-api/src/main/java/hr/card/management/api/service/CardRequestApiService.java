package hr.card.management.api.service;

import hr.card.management.api.model.CardRequestCommand;
import hr.card.management.api.model.CardRequestCommandByOIB;
import hr.card.management.api.model.CardRequestDto;
import hr.card.management.api.model.ResponseBaseDto;

import java.util.List;

public interface CardRequestApiService {

    CardRequestDto createCardRequest(CardRequestCommand command);

    CardRequestDto findCardRequestById(Long id);

    List<CardRequestDto> findAll();

    ResponseBaseDto findCardRequestsByOib(CardRequestCommandByOIB oib);

    void deleteCardRequest(Long id);
}
