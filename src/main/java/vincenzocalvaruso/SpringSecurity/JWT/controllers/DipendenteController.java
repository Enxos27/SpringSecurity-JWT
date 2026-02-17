package vincenzocalvaruso.SpringSecurity.JWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.DipendenteDTO;
import vincenzocalvaruso.SpringSecurity.JWT.service.DipendenteService;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {
    @Autowired
    private DipendenteService dipendenteService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
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

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/{id}")
    public Dipendente getById(@PathVariable long id) {
        return dipendenteService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        dipendenteService.findByIdAndDelete(id);
    }

    @PutMapping("/me")
    public Dipendente updateProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedDipendente, @RequestBody DipendenteDTO payload) {
        return this.dipendenteService.findByIdAndUpdate(currentAuthenticatedDipendente.getId(), payload);
    }

    @DeleteMapping("/me")
    public void deleteProfile(@AuthenticationPrincipal Dipendente currentAuthenticatedUser) {
        this.dipendenteService.findByIdAndDelete(currentAuthenticatedUser.getId());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public Dipendente findByIdAndUpdate(@PathVariable long userId, @RequestBody DipendenteDTO payload) {
        return this.dipendenteService.findByIdAndUpdate(userId, payload);
    }


//    @PatchMapping("/{dipendenteId}/avatar")
//    public Dipendente uploadImage(@PathVariable long dipendenteId, @RequestParam("file") MultipartFile file) {
//        String url = this.dipendenteService.uploadImage(file);
//        return dipendenteService.patchAvatar(dipendenteId, url);
//    }

}
