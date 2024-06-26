package hr.card.management.web.ui.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestCommand {

    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    private String oib;
}
