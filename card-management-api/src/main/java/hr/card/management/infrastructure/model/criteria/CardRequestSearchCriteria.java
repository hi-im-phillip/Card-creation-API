package hr.card.management.infrastructure.model.criteria;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardRequestSearchCriteria extends SearchCriteria {

    private String oib;
}
