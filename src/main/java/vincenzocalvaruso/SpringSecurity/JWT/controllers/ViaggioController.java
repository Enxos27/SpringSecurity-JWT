package vincenzocalvaruso.SpringSecurity.JWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Viaggio;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.ValidationException;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.ViaggioDTO;
import vincenzocalvaruso.SpringSecurity.JWT.service.ViaggioService;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    // 1. Creazione Viaggio
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio create(@RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        }
        return viaggioService.save(body);
    }

    // 2. Recupero tutti i viaggi
    @GetMapping
    public List<Viaggio> getAll() {
        return viaggioService.findAll();
    }

    // 3. Recupero un singolo viaggio
    @GetMapping("/{id}")
    public Viaggio getById(@PathVariable long id) {
        return viaggioService.findById(id);
    }

    // 4. Modifico un viaggio specifico (PUT)
    @PutMapping("/{id}")
    public Viaggio update(@PathVariable long id, @RequestBody @Validated ViaggioDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        }
        return viaggioService.findByIdAndUpdate(id, body);
    }

    // 5. Cambio stato (PATCH) - Es. Da "In programma" a "Completato"
    @PatchMapping("/{id}/{stato}")
    public Viaggio toggleStato(@PathVariable long id, @PathVariable String stato) {
        return viaggioService.toggleStato(id, stato);
    }

    // 6. Cancello un viaggio
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        viaggioService.findByIdAndDelete(id);
    }
}