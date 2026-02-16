package vincenzocalvaruso.SpringSecurity.JWT.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dipendenti")
@Getter
@Setter
@NoArgsConstructor
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String nome;
    private String cognome;

    @Column(nullable = false, unique = true)
    private String email;

    private String avatar; // URL di Cloudinary

    private String password;

    public Dipendente(String username, String nome, String cognome, String email, String password) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
}