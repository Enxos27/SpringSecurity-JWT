package vincenzocalvaruso.SpringSecurity.JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Prenotazione;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Viaggio;

import java.time.LocalDate;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    // Questo metodo controlla se esiste già una prenotazione per quel dipendente in quella specifica data del viaggio
    boolean existsByDipendenteAndViaggioData(Dipendente dipendente, LocalDate data);

    boolean existsByDipendente(Dipendente dipendente);

    boolean existsByViaggio(Viaggio viaggio);
}