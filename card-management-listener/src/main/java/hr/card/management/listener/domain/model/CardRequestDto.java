package hr.card.management.listener.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    private String oib;
}
