package vincenzocalvaruso.SpringSecurity.JWT.payloads;

import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO(
        @NotNull(message = "L'ID del viaggio è obbligatorio")
        Long viaggioId,

        @NotNull(message = "L'ID del dipendente è obbligatorio")
        Long dipendenteId,

        String note //Le note non sono obbligatorie quindi non metto nulla

) {
}
