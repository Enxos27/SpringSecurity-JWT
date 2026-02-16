package vincenzocalvaruso.SpringSecurity.JWT.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.UnauthorizedException;

import java.io.IOException;

@Component
public class JWTFilters extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Questo è il metodo che viene eseguito a ogni richiesta
        // Sarà questo metodo quindi che dovrà fare il controllo dei token

        // PIANO DI BATTAGLIA

        // 1. Verifichiamo se la richiesta contiene l'header Authorization e che in caso sia nel formato "Bearer oi1j3oj21o3j213jo12j3"
        // Se l'header non c'è oppure se è malformato --> lanciamo eccezione
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new UnauthorizedException("Inserire il token nell'Authorization header nel formato corretto");

        // 2. Estrarre il token dall'header, diversi metodi di manipolazione di stringhe
        //  authHeader -> "Bearer oi1j3oj21o3j213jo12j3"  <-- vogliamo prendere tutto tranne "Bearer " <- ATTENZIONE ALLO SPAZIO
        String accessToken = authHeader.replace("Bearer ", "");

        // 3. Verifichiamo se il token è valido (controllare la firma e verificare data di scadenza)
        jwtTools.verificaToker(accessToken);

        // 4. Se tutto è OK --> andiamo avanti, trasmettiamo la richiesta al prossimo (può essere o un altro elemento della catena oppure il controller)
        filterChain.doFilter(request, response);

        // 5. Se c'è qualche problema con il token -> eccezione
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return new AntPathMatcher().match("/user/**", request.getServletPath());
        // return request.getServletPath().equals("/auth/login") || request.getServletPath().equals("/auth/register");
    }
}

