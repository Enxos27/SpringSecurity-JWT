package vincenzocalvaruso.SpringSecurity.JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.entities.Dipendente;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.entities.Prenotazione;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.entities.Viaggio;

import java.time.LocalDate;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    // Questo metodo controlla se esiste già una prenotazione per quel dipendente in quella specifica data del viaggio
    boolean existsByDipendenteAndViaggioData(Dipendente dipendente, LocalDate data);

    boolean existsByDipendente(Dipendente dipendente);

    boolean existsByViaggio(Viaggio viaggio);
}