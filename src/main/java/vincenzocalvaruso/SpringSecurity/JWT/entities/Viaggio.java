package vincenzocalvaruso.SpringSecurity.JWT.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "viaggi")
@Getter
@Setter
@NoArgsConstructor
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String destinazione;
    private LocalDate data;

    // indica lo stato del progetto: "completato, programmato, rimandato, cancellato"
    private String stato;
}
