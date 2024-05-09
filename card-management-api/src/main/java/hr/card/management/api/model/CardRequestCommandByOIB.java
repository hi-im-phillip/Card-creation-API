package hr.card.management.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
    private String oib;
}
