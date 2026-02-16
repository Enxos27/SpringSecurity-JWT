package vincenzocalvaruso.SpringSecurity.JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Viaggio;

public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
}
