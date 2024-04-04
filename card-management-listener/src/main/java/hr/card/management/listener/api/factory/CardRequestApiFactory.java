package hr.card.management.listener.api.factory;


import hr.card.management.listener.api.model.CardRequestCommand;
import hr.card.management.listener.api.model.CardRequestDto;

public final class CardRequestApiFactory {

    private CardRequestApiFactory() {
    }

    public static CardRequestCommand toCardRequestCommand(CardRequestDto command) {
        return CardRequestCommand.builder()
                .id(command.getId())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .status(command.getStatus())
                .oib(command.getOib())
                .build();
    }
}

