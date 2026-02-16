package vincenzocalvaruso.SpringSecurity.JWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Viaggio;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.BadRequestException;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.NotFoundException;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.ViaggioDTO;
import vincenzocalvaruso.SpringSecurity.JWT.repository.PrenotazioneRepository;
import vincenzocalvaruso.SpringSecurity.JWT.repository.ViaggioRepository;

import java.util.List;

@Service
public class ViaggioService {
    @Autowired
    private ViaggioRepository viaggioRepo;

    @Autowired
    private PrenotazioneRepository prenotazioneRepo;

    public Viaggio save(ViaggioDTO body) {
        Viaggio nuovo = new Viaggio();
        nuovo.setDestinazione(body.destinazione());
        nuovo.setData(body.data());
        nuovo.setStato("IN_PROGRAMMA"); // Nasce sempre come "In programma"
        return viaggioRepo.save(nuovo);
    }

    // 2. Recupero tutti i record
    public List<Viaggio> findAll() {
        return viaggioRepo.findAll();
    }

    // 3. Recupero un record
    public Viaggio findById(long id) {
        return viaggioRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // 4. Aggiornamento (PUT)
    public Viaggio findByIdAndUpdate(long id, ViaggioDTO body) {
        Viaggio found = this.findById(id);
        found.setDestinazione(body.destinazione());
        found.setData(body.data());
        // Lo stato 'completato' lo cambio tramite Patch e non tramite Put
        return viaggioRepo.save(found);
    }

    // 5. Cambio stato (PATCH)
//    public Viaggio toggleStato(long id) {
//        Viaggio found = this.findById(id);
//        // Se è true diventa false, se è false diventa true
//        found.setCompletato(!found.isCompletato());
//        return viaggioRepo.save(found);
//    }

    // 5. Cambio stato (PATCH)
    public Viaggio toggleStato(long id, String stato) {
        Viaggio found = this.findById(id);
        found.setStato(stato);
        return viaggioRepo.save(found);
    }

    // 6. Cancellazione con controllo
    public void findByIdAndDelete(long id) {
        Viaggio found = this.findById(id);

        // Verifico se ci sono prenotazioni per questo viaggio
        boolean hasPrenotazioni = prenotazioneRepo.existsByViaggio(found);

        if (hasPrenotazioni) {
            throw new BadRequestException("Impossibile eliminare il viaggio: ci sono dipendenti già prenotati!");
        }

        viaggioRepo.delete(found);
    }
}
