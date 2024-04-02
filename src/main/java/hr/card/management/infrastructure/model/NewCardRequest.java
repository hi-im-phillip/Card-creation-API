package hr.card.management.infrastructure.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "new_card_request")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class NewCardRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "First name contains illegal characters")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "Last name contains illegal characters")
    private String lastName;
    private String status;

    @NotBlank(message = "OIB cannot be empty")
    @Pattern(regexp = "\\b(?:\\d{11}|HR\\d{11})\\b", message = "OIB contains illegal characters")
    @Column(unique = true)
    private String oib;
}
