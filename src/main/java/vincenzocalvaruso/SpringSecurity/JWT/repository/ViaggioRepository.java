package vincenzocalvaruso.SpringSecurity.JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vincenzocalvaruso.Development_of_a_SpringWeb_Service.entities.Viaggio;

public interface ViaggioRepository extends JpaRepository<Viaggio, Long> {
}
