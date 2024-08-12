package com.strong.AuthorService.Security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.strong.AuthorService.Service.AuthorService;

/**
 * Security configuration class for setting up the security aspects of the
 * application.
 * This class configures security settings such as CORS, CSRF, authentication,
 * and authorization.
 * It uses the Spring Security framework to define a security filter chain and
 * other security-related beans.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Value("${bloodDonation.Cors.Url}")
    private String CORS_URL;

    @Value("${bloodDonation.Cors.Methods}")
    private String CORS_METHODS;

    private final AuthorService authorService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    private CustomLogoutHandler logoutHandler;

    /**
     * Constructor for SecurityConfig.
     *
     * @param authorService    the service used for user authentication.
     * @param jwtRequestFilter the filter for JWT-based authentication.
     */
    public SecurityConfig(@Lazy AuthorService authorService, JwtRequestFilter jwtRequestFilter) {
        this.authorService = authorService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configures the security filter chain with custom settings.
     * 
     * @param http the HttpSecurity object to configure.
     * @return the configured SecurityFilterChain.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/api/accessToken").permitAll()
                        .requestMatchers("/api/actuator/health").permitAll()
                        .requestMatchers(HttpMethod.POST, "/**").permitAll()
                        .anyRequest().permitAll())
                .userDetailsService(authorService)
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .logout(logout -> logout
                        .logoutUrl("/api/v1/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(
                                (request, response, authentication) -> SecurityContextHolder.clearContext()));
        return http.build();
    }

    /**
     * Provides the CORS configuration source for the application.
     * 
     * @return the CORS configuration source.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        List<String> allowedOrigins = Arrays.asList(CORS_URL.split(","));
        config.setAllowedOrigins(allowedOrigins);
        config.setAllowedMethods(Arrays.asList(CORS_METHODS.split(",")));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setMaxAge(3600L);
        config.setExposedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION));
        return request -> config;
    }

    /**
     * Provides a PasswordEncoder bean using BCrypt hashing.
     * 
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Provides an AuthenticationManager bean.
     * 
     * @param configuration the AuthenticationConfiguration used to build the
     *                      AuthenticationManager.
     * @return the configured AuthenticationManager.
     * @throws Exception if an error occurs while building the
     *                   AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
