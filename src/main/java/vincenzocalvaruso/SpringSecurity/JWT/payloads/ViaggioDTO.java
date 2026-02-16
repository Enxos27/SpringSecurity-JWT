package vincenzocalvaruso.SpringSecurity.JWT.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioDTO(

        @NotBlank(message = "La destinazione è obbligatoria")
        String destinazione,
        @NotNull(message = "La data del viaggio è obbligatoria")
        LocalDate data
) {
}
