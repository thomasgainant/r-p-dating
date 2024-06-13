package fr.twothirds.rpd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.authorizeHttpRequests((authz) -> authz
            .requestMatchers(
                new AntPathRequestMatcher("/register"),
                new AntPathRequestMatcher("/confirm"),
                new AntPathRequestMatcher("/login")
            ).permitAll()       
            .requestMatchers(
                new AntPathRequestMatcher("/actuator/**")
            ).permitAll()
            .requestMatchers(
                new AntPathRequestMatcher("/h2-console/**")
            ).permitAll()
            //TODO unmock
            .requestMatchers(
                new AntPathRequestMatcher("/api/**")
            ).permitAll()
            .anyRequest().authenticated()
        );

        http.csrf((csrf) ->
                csrf.ignoringRequestMatchers(
                        new AntPathRequestMatcher("/h2-console/**")
                ).csrfTokenRepository(
                        CookieCsrfTokenRepository.withHttpOnlyFalse()
                )
        );
        
        http.headers((headers) -> headers
                .frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::disable
                )
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
