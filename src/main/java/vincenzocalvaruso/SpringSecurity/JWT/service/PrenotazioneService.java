package vincenzocalvaruso.SpringSecurity.JWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Prenotazione;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Viaggio;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.BadRequestException;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.NotFoundException;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.PrenotazioneDTO;
import vincenzocalvaruso.SpringSecurity.JWT.repository.PrenotazioneRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepo;

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private ViaggioService viaggioService;

    // 1. Assegnazione dipendente a viaggio
    public Prenotazione save(PrenotazioneDTO body) {
        // Recupero il dipendente e il viaggio
        Dipendente dipendente = dipendenteService.findById(body.dipendenteId());
        Viaggio viaggio = viaggioService.findById(body.viaggioId());

        // LOGICA: Un dipendente non può avere più prenotazioni per lo stesso giorno
        // Verifico se esiste già una prenotazione per questo dipendente nella data del viaggio scelto
        boolean giaImpegnato = prenotazioneRepo.existsByDipendenteAndViaggioData(dipendente, viaggio.getData());

        if (giaImpegnato) {
            throw new BadRequestException("Il dipendente " + dipendente.getNome() +
                    " ha già un impegno per la data " + viaggio.getData());
        }

        // Creazione della prenotazione
        Prenotazione nuovaPrenotazione = new Prenotazione();
        nuovaPrenotazione.setDipendente(dipendente);
        nuovaPrenotazione.setViaggio(viaggio);
        nuovaPrenotazione.setDataRichiesta(LocalDate.now());
        nuovaPrenotazione.setNote(body.note());

        return prenotazioneRepo.save(nuovaPrenotazione);
    }

    // 2. Recupero tutte le prenotazioni
    public List<Prenotazione> findAll() {
        return prenotazioneRepo.findAll();
    }

    // 3. Rrecupero singola prenotazione
    public Prenotazione findById(long id) {
        return prenotazioneRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // 4. Cancello una determinata prenotazione tramite id
    public void findByIdAndDelete(long id) {
        Prenotazione found = this.findById(id);
        prenotazioneRepo.delete(found);
    }
}
