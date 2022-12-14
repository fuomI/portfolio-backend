package fi.jonij.portfoliobackend.security;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Map;
import java.util.function.Function;

@Configuration
public class SpringSecurityConfiguration {

    private Credentials credentials = new Credentials();

    // Iterates through credentialsMap and adds new users based on credentials
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager =
                new InMemoryUserDetailsManager();

        Map<String, String> credentialsMap = credentials.getCredentials();
        for (Map.Entry<String, String> entry : credentialsMap.entrySet()) {
            UserDetails userDetails = createNewUser(entry.getKey(), entry.getValue());
            inMemoryUserDetailsManager.createUser(userDetails);
        }

        return inMemoryUserDetailsManager;
    }

    // Method for creating user with bcrypt encoder and two roles: "USER" & "ADMIN"
    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder =
                input -> passwordEncoder().encode(input);

        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();

        return userDetails;
    }

    // Method for creating bcrypt password encoder with default strength 10
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
