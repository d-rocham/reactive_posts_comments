package org.alpha.application.generic.models.usecases;

import lombok.RequiredArgsConstructor;
import org.alpha.application.adapters.repository.UserRepository;
import org.alpha.application.generic.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CreateUserUseCase {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public Mono<User> save(User u, String role) {
        return this.userRepository
                .save(u.toBuilder()
                        .password(passwordEncoder.encode(u.getPassword()))
                        .email(u.getUsername())
                        .roles(new ArrayList<>() {{
                            add(role);
                        }}).build());
    }
}
