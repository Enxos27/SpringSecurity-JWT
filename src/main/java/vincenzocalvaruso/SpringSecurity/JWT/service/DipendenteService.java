package vincenzocalvaruso.SpringSecurity.JWT.service;

//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vincenzocalvaruso.SpringSecurity.JWT.entities.Dipendente;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.BadRequestException;
import vincenzocalvaruso.SpringSecurity.JWT.exceptions.NotFoundException;
import vincenzocalvaruso.SpringSecurity.JWT.payloads.DipendenteDTO;
import vincenzocalvaruso.SpringSecurity.JWT.repository.DipendenteRepository;
import vincenzocalvaruso.SpringSecurity.JWT.repository.PrenotazioneRepository;

import java.util.List;

@Service

public class DipendenteService {
    @Autowired
    private DipendenteRepository dipendenteRepo;
    //    @Autowired
//    private Cloudinary cloudinaryUploader;
    @Autowired
    private PrenotazioneRepository prenotazioneRepo;


    public List<Dipendente> findAll() {
        return this.dipendenteRepo.findAll();
    }

    public Dipendente save(@Valid DipendenteDTO body) {

        if (dipendenteRepo.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso!");
        }
        if (dipendenteRepo.existsByUsername(body.username())) {
            throw new BadRequestException("L'username " + body.username() + " è già in uso!");
        }
        Dipendente newDipendente = new Dipendente(body.username(), body.nome(), body.cognome(), body.email(), body.password());
        return dipendenteRepo.save(newDipendente);
    }

    public Dipendente findById(long id) {
        return dipendenteRepo.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Dipendente findByIdAndUpdate(long id, @Valid DipendenteDTO payload) {
        Dipendente found = this.findById(id);

        if (!found.getEmail().equals(payload.email())) {
            if (this.dipendenteRepo.existsByEmail(payload.email())) {
                throw new BadRequestException("L'email " + payload.email() + " è già in uso!");
            }
        }
        if (!found.getUsername().equals(payload.username())) {
            if (this.dipendenteRepo.existsByUsername(payload.username())) {
                throw new BadRequestException("L'username " + payload.username() + " è già in uso!");
            }
        }
        found.setUsername(payload.username());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());

        return dipendenteRepo.save(found);
    }

    public void findByIdAndDelete(long id) {
        Dipendente found = this.findById(id);

        boolean hasPrenotazioni = prenotazioneRepo.existsByDipendente(found);
        if (hasPrenotazioni) {
            throw new BadRequestException("Non è possibile cancellare il dipendente perché ha dei viaggi prenotati. Cancella prima le sue prenotazioni!");
        }

        dipendenteRepo.delete(found);
    }

    public Dipendente findByUsername(String username) {
        return this.dipendenteRepo.findByUsername(username).orElseThrow(() -> new NotFoundException("L'utente con username:  " + username + " non è statp trovato"));
    }

//    public String uploadImage(MultipartFile file) {
//
//        try {
//            Map imageUrl = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
//
//            String url = (String) imageUrl.get("secure_url");
//            return url;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    public Dipendente patchAvatar(long id, String url) {
//        Dipendente found = this.findById(id);
//        found.setAvatar(url);
//        return dipendenteRepo.save(found);
//    }
}
