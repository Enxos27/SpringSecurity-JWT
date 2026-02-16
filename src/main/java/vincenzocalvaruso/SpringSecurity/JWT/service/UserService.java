package vincenzocalvaruso.SpringSecurity.JWT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.UnauthorizedException;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.LoginDTO;
import vincenzocalvaruso.SpringSecurity.JWT.security.JWTTools;

@Service
public class UserService {
    @Autowired
    private DipendenteService dipendenteService;
    @Autowired
    private JWTTools jwtTools;

    public String checkCredenzialAndReturnToken(LoginDTO body) {
        //1- controllo che esiste un utente con quella email, se esiste controllo che la password sia uguale
        //Se credenziali non ok genero exception --> 401 UnAuthorized
        Dipendente found = dipendenteService.findByUsername(body.username());
        if (found.getPassword().equalsIgnoreCase(body.password())) {
            //TODO: LA PASSWORDO COSI è MOMENTANEA, MIGLIORERò CIò
            //2- creo token
            String token = jwtTools.generaToken(found);

            return token;


        } else {
            throw new UnauthorizedException("Credenziali non valide");
        }


    }
}
