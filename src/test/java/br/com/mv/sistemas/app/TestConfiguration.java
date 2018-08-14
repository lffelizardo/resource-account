package br.com.mv.sistemas.app;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.mv.sistemas.app.repository.UserRepository;
import br.com.mv.sistemas.app.services.UserService;

@Configuration
public class TestConfiguration {

	@Bean
    public UserService userService() {
        return Mockito.mock(UserService.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }
}
