package fi.jonij.portfoliobackend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Map;
import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

    private final UserCredentialsService userCredentialsService;

    public SpringSecurityConfiguration(UserCredentialsService userCredentialsService) {
        this.userCredentialsService = userCredentialsService;
    }

    // Iterates through credentialsMap and adds new users based on credentials
    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager =
                new InMemoryUserDetailsManager();

        Map<String, String> credentialsMap = userCredentialsService.getCredentials();
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

        return User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
    }

    // Method for creating bcrypt password encoder with default strength 10
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Protects all URLs, login form shown for unauthorized requests,
    // CSRF disabled, frames enabled from same origin (H2 Works)
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                auth -> auth.antMatchers("/rest/**").permitAll()
                        .anyRequest().authenticated());

        http.formLogin(withDefaults());
        
        http.headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        return http.build();
    }
}
