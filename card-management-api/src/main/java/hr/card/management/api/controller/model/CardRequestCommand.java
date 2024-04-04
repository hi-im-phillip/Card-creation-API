package hr.card.management.api.controller.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardRequestCommand {

    private Long id;
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "First name contains illegal characters")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "Last name contains illegal characters")
    private String lastName;
    @NotBlank(message = "Last name cannot be empty")
    private String status;
    @NotBlank(message = "OIB cannot be empty")
    @Pattern(regexp = "\\b(?:\\d{11}|HR\\d{11})\\b", message = "OIB contains illegal characters")
    private String oib;
}
