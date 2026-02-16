package vincenzocalvaruso.SpringSecurity.JWT.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {
    @Bean
    public Cloudinary getImageUploader(@Value("${cloudinary.name}") String cloudName,
                                       @Value("${cloudinary.apikey}") String apiKey,
                                       @Value("${cloudinary.secret}") String apiSecret) {
        System.out.println(cloudName); //Per verificare che i valori siano giusti
        Map<String, String> configurazione = new HashMap<>();
        configurazione.put("cloud_name", cloudName);
        configurazione.put("api_key", apiKey);
        configurazione.put("api_secret", apiSecret);

        return new Cloudinary(configurazione);
    }
}
