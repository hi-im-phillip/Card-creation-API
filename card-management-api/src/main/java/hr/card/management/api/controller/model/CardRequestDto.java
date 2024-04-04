package hr.card.management.api.controller.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardRequestDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    private String oib;
}
