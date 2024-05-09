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
public class CardRequestCommandByOIB extends RequestBaseDto {

    @Schema(description = "OIB (Personal Identification Number) of the card request", example = "12345678901")
    @NotBlank(message = "OIB cannot be empty")
    @Pattern(regexp = "\\b(?:\\d{11}|HR\\d{11})\\b", message = "OIB contains illegal characters")
    private String oib;
}
