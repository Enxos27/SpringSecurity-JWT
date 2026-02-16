package vincenzocalvaruso.SpringSecurity.JWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.entities.Dipendente;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.exceptions.ValidationException;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.payloads.DipendenteDTO;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.service.DipendenteService;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping
    public List<Dipendente> getAll() {
        return dipendenteService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente create(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {
            validationResult.getFieldErrors().forEach(err ->
                    System.out.println("Campo: " + err.getField() + " - Errore: " + err.getDefaultMessage())
            );
            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return dipendenteService.save(body);
        }
    }

    @GetMapping("/{id}")
    public Dipendente getById(@PathVariable long id) {
        return dipendenteService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        dipendenteService.findByIdAndDelete(id);
    }

    @PatchMapping("/{dipendenteId}/avatar")
    public Dipendente uploadImage(@PathVariable long dipendenteId, @RequestParam("file") MultipartFile file) {
        String url = this.dipendenteService.uploadImage(file);
        return dipendenteService.patchAvatar(dipendenteId, url);
    }

}
