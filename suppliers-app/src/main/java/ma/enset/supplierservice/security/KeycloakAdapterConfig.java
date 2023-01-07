package ma.enset.supplierservice.security;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdapterConfig {
    //it tells tha app to take into consideration the configuration in application.properties instead of keycloak.json
    @Bean
    public KeycloakSpringBootConfigResolver springBootConfigResolver(){
        return  new KeycloakSpringBootConfigResolver();
    }
}
