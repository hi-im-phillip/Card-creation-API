package hr.card.management.api.infrastructure.model.criteria;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequestSearchCriteria extends SearchCriteria {

    private String oib;
}
