package vincenzocalvaruso.SpringSecurity.JWT.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "prenotazioni")
@Getter
@Setter
@NoArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne // Relazione UNIDIREZIONALE: Prenotazione conosce Viaggio, ma Viaggio non conosce Prenotazione
    @JoinColumn(name = "viaggio_id")
    private Viaggio viaggio;

    @ManyToOne // Relazione UNIDIREZIONALE
    @JoinColumn(name = "dipendente_id")
    private Dipendente dipendente;

    private LocalDate dataRichiesta;
    private String note;
}
