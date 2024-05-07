package hr.card.management.api.controller.service;

import hr.card.management.api.controller.model.CardRequestCommand;
import hr.card.management.api.controller.model.CardRequestCommandByOIB;
import hr.card.management.api.controller.model.CardRequestDto;
import hr.card.management.api.controller.model.ResponseBaseDto;

import java.util.List;

public interface CardRequestApiService {

    CardRequestDto createCardRequest(CardRequestCommand command);

    CardRequestDto findCardRequestById(Long id);

    List<CardRequestDto> findAll();

    ResponseBaseDto findCardRequestsByOib(CardRequestCommandByOIB oib);

    void deleteCardRequest(Long id);
}
