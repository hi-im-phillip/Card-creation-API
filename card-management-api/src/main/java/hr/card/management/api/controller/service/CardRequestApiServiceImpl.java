package hr.card.management.api.controller.service;

import hr.card.management.api.controller.factory.CardRequestApiFactory;
import hr.card.management.api.controller.model.CardRequestCommand;
import hr.card.management.api.controller.model.CardRequestCommandByOIB;
import hr.card.management.api.controller.model.CardRequestDto;
import hr.card.management.api.controller.model.ResponseBaseDto;
import hr.card.management.api.domain.annotations.Cacheable;
import hr.card.management.api.domain.annotations.PerformanceLogger;
import hr.card.management.api.infrastructure.model.CardRequest;
import hr.card.management.api.infrastructure.model.criteria.CardRequestSearchCriteria;
import hr.card.management.api.infrastructure.repository.CardRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CardRequestApiServiceImpl implements CardRequestApiService {

    private final CardRequestRepository cardRequestRepository;

    @Override
    @PerformanceLogger
    public CardRequestDto createCardRequest(CardRequestCommand command) {
        return CardRequestApiFactory.toCardRequestDto(cardRequestRepository.save(CardRequestApiFactory.toCardRequest(command)));
    }


    @Override
    @Cacheable
    public CardRequestDto findCardRequestById(Long id) {
        return cardRequestRepository.findById(id).map(CardRequestApiFactory::toCardRequestDto).orElse(null);
    }

    @Override
    @PerformanceLogger
    public List<CardRequestDto> findAll() {
        List<CardRequest> cardRequestList = new ArrayList<>();
        cardRequestRepository.findAll().forEach(cardRequestList::add);
        return CardRequestApiFactory.toCardRequestDtoList(cardRequestList);
    }

    @Override
    @PerformanceLogger
    @Cacheable
    public ResponseBaseDto findCardRequestsByOib(CardRequestCommandByOIB command) {

        var cardRequestSearchCriteria = CardRequestSearchCriteria
                .builder()
                .oib(command.getOib())
                .build();

        Pageable pageable = PageRequest.of(command.getPage(), command.getPerPage());
        Page<CardRequest> cardRequests = cardRequestRepository.findEntitiesBySearchCriteria(cardRequestSearchCriteria, pageable);

        return new ResponseBaseDto(
                cardRequests.getContent()
                        .stream()
                        .map(CardRequestApiFactory::toCardRequestDto)
                        .toList(),
                (int) cardRequests.getTotalElements());
    }

    @Override
    public void deleteCardRequest(Long id) {
        cardRequestRepository.deleteById(id);
    }
}
