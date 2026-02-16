package vincenzocalvaruso.SpringSecurity.JWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.service.DipendenteService;

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

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public Dipendente create(@RequestBody @Validated DipendenteDTO body, BindingResult validationResult) {
//
//        if (validationResult.hasErrors()) {
//            validationResult.getFieldErrors().forEach(err ->
//                    System.out.println("Campo: " + err.getField() + " - Errore: " + err.getDefaultMessage())
//            );
//            List<String> errorsList = validationResult.getFieldErrors()
//                    .stream()
//                    .map(fieldError -> fieldError.getDefaultMessage())
//                    .toList();
//
//            throw new ValidationException(errorsList);
//        } else {
//            return dipendenteService.save(body);
//        }
//    }

    @GetMapping("/{id}")
    public Dipendente getById(@PathVariable long id) {
        return dipendenteService.findById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        dipendenteService.findByIdAndDelete(id);
    }

//    @PatchMapping("/{dipendenteId}/avatar")
//    public Dipendente uploadImage(@PathVariable long dipendenteId, @RequestParam("file") MultipartFile file) {
//        String url = this.dipendenteService.uploadImage(file);
//        return dipendenteService.patchAvatar(dipendenteId, url);
//    }

}
