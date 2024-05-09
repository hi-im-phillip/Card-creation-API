package hr.card.management.api.model;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "DTO representing a card request")
public class CardRequestDto {

    private Long id;
    @Schema(description = "First name of the card requester", example = "John")
    @NotBlank(message = "First name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "First name contains illegal characters")
    private String firstName;
    @Schema(description = "Last name of the card requester", example = "Doe")
    @NotBlank(message = "Last name cannot be empty")
    @Pattern(regexp = "[A-Za-z-']*", message = "Last name contains illegal characters")
    private String lastName;
    @Schema(description = "Status of the card request", example = "PROCESSING")
    @NotBlank(message = "Last name cannot be empty")
    private String status;
    @Schema(description = "OIB (Personal Identification Number) of the card request", example = "12345678901")
    @NotBlank(message = "OIB cannot be empty")
    @Pattern(regexp = "\\b(?:\\d{11}|HR\\d{11})\\b", message = "OIB contains illegal characters")
    private String oib;
}
