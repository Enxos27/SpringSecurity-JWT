package vincenzocalvaruso.SpringSecurity.JWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.ValidationException;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.DipendenteDTO;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.LoginDTO;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.LoginRespondeDTO;
import vincenzocalvaruso.SpringSecurity.JWT.service.DipendenteService;
import vincenzocalvaruso.SpringSecurity.JWT.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public LoginRespondeDTO login(@RequestBody LoginDTO body) {
        return new LoginRespondeDTO(this.userService.checkCredenzialAndReturnToken(body));
    }

    @PostMapping("/register")
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
}
