package vincenzocalvaruso.SpringSecurity.JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;

import java.util.Optional;

public interface DipendenteRepository extends JpaRepository<Dipendente, Long> {
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Dipendente> findByUsername(String username);
}
