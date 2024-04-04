package hr.card.management.api.controller.service;

import hr.card.management.api.controller.factory.CardRequestApiFactory;
import hr.card.management.api.controller.model.CardRequestCommand;
import hr.card.management.api.controller.model.CardRequestDto;
import hr.card.management.api.infrastructure.model.CardRequest;
import hr.card.management.api.infrastructure.repository.CardRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CardRequestApiServiceImpl implements CardRequestApiService {

    private final CardRequestRepository cardRequestRepository;

    @Override
    public CardRequestDto createCardRequest(CardRequestCommand command) {
        return CardRequestApiFactory.toCardRequestDto(cardRequestRepository.save(CardRequestApiFactory.toCardRequest(command)));
    }

    @Override
    public CardRequestDto findCardRequestById(Long id) {
        return cardRequestRepository.findById(id).map(CardRequestApiFactory::toCardRequestDto).orElse(null);
    }

    @Override
    public List<CardRequestDto> findAll() {
        List<CardRequest> cardRequestList = new ArrayList<>();
        cardRequestRepository.findAll().forEach(cardRequestList::add);
        return CardRequestApiFactory.toCardRequestDtoList(cardRequestList);
    }

    @Override
    public CardRequestDto findCardRequestsByOib(String oib) {
        return cardRequestRepository.findNewCardRequestByOib(oib).map(CardRequestApiFactory::toCardRequestDto).orElse(null);
    }

    @Override
    public void deleteCardRequest(Long id) {
        cardRequestRepository.deleteById(id);
    }
}
