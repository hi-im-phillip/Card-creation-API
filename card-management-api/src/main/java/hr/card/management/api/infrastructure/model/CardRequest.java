package hr.card.management.api.infrastructure.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "card_request")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class CardRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String status;
    @Column(unique = true)
    private String oib;
}
