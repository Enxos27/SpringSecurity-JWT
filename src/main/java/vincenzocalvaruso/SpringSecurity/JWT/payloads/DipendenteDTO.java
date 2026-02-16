package vincenzocalvaruso.SpringSecurity.JWT.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotBlank(message = "Lo username è obbligatorio")
        @Size(min = 4, message = "Lo username deve avere almeno 4 caratteri")
        String username,
        @NotBlank(message = "Il nome è obbligatorio")
        String nome,
        @NotBlank(message = "Il cognome è obbligatorio")
        String cognome,
        @Email(message = "L'email inserita non è valida")
        @NotBlank(message = "L'email è obbligatoria")
        String email
) {
}
