package hr.card.management.api.controller.factory;

import hr.card.management.api.controller.model.CardRequestCommand;
import hr.card.management.api.controller.model.CardRequestDto;
import hr.card.management.api.infrastructure.model.CardRequest;

import java.util.List;
import java.util.stream.Collectors;

public final class CardRequestApiFactory {

    private CardRequestApiFactory() {
    }

    public static CardRequestDto toCardRequestDto(CardRequest cardRequest) {
        return CardRequestDto.builder()
                .id(cardRequest.getId())
                .firstName(cardRequest.getFirstName())
                .lastName(cardRequest.getLastName())
                .status(cardRequest.getStatus())
                .oib(cardRequest.getOib())
                .build();
    }

    public static List<CardRequestDto> toCardRequestDtoList(List<CardRequest> cardRequestList) {
        return cardRequestList.stream().map(CardRequestApiFactory::toCardRequestDto).collect(Collectors.toList());
    }

    public static CardRequest toCardRequest(CardRequestCommand command) {
        return CardRequest.builder()
                .id(command.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .status(command.getStatus())
                .oib(command.getOib())
                .build();
    }
}

