package ptit.example.bachhoaxanhbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ptit.example.bachhoaxanhbackend.storage.StorageProperties;
import ptit.example.bachhoaxanhbackend.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class BachHoaXanhBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BachHoaXanhBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
//            storageService.deleteAll();
            storageService.init();
        };
    }

}
