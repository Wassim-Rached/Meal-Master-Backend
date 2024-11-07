package com.dsi301.mealmasterserver.configuration;

import com.dsi301.mealmasterserver.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;

@Configuration
public class SecurityConfiguration {
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                // OPTIONS requests should be allowed
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                // health check
                                .requestMatchers(HttpMethod.GET, "/api/health").permitAll()
                                // POST /api/accounts
                                .requestMatchers(HttpMethod.POST, "/api/accounts").permitAll()
                                // POST /api/auth/login
                                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                                // GET /api/ingredients
                                .requestMatchers(HttpMethod.GET, "/api/ingredients").permitAll()
                                // GET /api/measurements-units
                                .requestMatchers(HttpMethod.GET, "/api/measurements-units").permitAll()
                                // GET /api/recipes
                                .requestMatchers(HttpMethod.GET, "/api/recipes").permitAll()
                                // POST /api/recipes
                                .requestMatchers("/api/recipes/**").permitAll()
                                .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
