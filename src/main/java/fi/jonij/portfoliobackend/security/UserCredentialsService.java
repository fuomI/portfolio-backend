package fi.jonij.portfoliobackend.security;

import fi.jonij.portfoliobackend.user.UserRepository;
import org.springframework.stereotype.Service;
import fi.jonij.portfoliobackend.user.User;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserCredentialsService {

    private final UserRepository userRepository;

    private Map<String, String> credentials;

    protected UserCredentialsService(UserRepository userRepository) {
        this.userRepository = userRepository;

        this.credentials = new HashMap<>();
        for (User user : userRepository.findAll()) {
            credentials.put(user.getUsername(), user.getPassword());
        }
    }

    protected Map<String, String> getCredentials() {
        return credentials;
    }

}
