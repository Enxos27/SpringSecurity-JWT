package vincenzocalvaruso.SpringSecurity.JWT.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.UnauthorizedException;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    // Jwts (proviene da jjwt-api) fornisce principalmente 2 metodi:
    // builder() --> lo usiamo per creare i token
    //  parser() --> lo usiamo per leggerli (ed estrarre info da essi) e validarli

    public String generaToken(Dipendente dipendente) {

        return Jwts.builder().setIssuedAt(new Date(System.currentTimeMillis()))
                // Data di emissione (IaT - Issued At), va messa in millisecondi
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                // 1000 millisecondi -> 1 secondo x 60 -> 1 minuto x 24 -> 1 giorno x 7 -> 1 settimana
                // Data di scadenza (Expiration Date) anche questa va messa in millisecondi
                .setSubject(String.valueOf(dipendente.getId()))
                // Subject cioè a chi appartiene il token.
                // Ci inseriamo l'id dell'utente (MAI METTERE DATI SENSIBILI AL SUO INTERNO)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                // Firmo il token fornendogli un segreto che il server conosce e usa per creare token
                // ma anche per verificarli
                .compact();
    }

    public void verificaToker(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (SignatureException ex) {
            // La firma non corrisponde (il token è stato manipolato o il segreto è cambiato)
            throw new UnauthorizedException("Firma del token non valida! Possibile manipolazione rilevata.");

        } catch (MalformedJwtException ex) {
            // Il token non ha la struttura corretta (mancano pezzi o non è un JWT)
            throw new UnauthorizedException("Il token è malformato! Controlla il formato dell'header Authorization.");

        } catch (Exception ex) {
            // Qualsiasi altro errore generico
            throw new UnauthorizedException("Problemi generici col token! Riprova.");
        }

    }
}
