package vincenzocalvaruso.SpringSecurity.JWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.entities.Prenotazione;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.exceptions.ValidationException;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.payloads.PrenotazioneDTO;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.service.PrenotazioneService;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {
    @Autowired
    private PrenotazioneService prenotazioneService;

    // 1. Crezione prenotazione
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione create(@RequestBody @Validated PrenotazioneDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        }
        return prenotazioneService.save(body);
    }

    // 2. Recupero tutte le prenotazioni
    @GetMapping
    public List<Prenotazione> getAll() {
        return prenotazioneService.findAll();
    }

    // 3. Recupero singola prenotazione
    @GetMapping("/{id}")
    public Prenotazione getById(@PathVariable long id) {
        return prenotazioneService.findById(id);
    }

    // 4. Cancellazione
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        prenotazioneService.findByIdAndDelete(id);
    }
}
