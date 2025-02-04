package com.vet.hc.api.shared.adapter.out.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.vet.hc.api.auth.core.adapter.in.filter.JwtRequestFilter;
import com.vet.hc.api.shared.application.properties.SecurityProperties;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityProperties securityProperties;
    private final JwtRequestFilter jwtRequestFilter;
    @Value("${spring.mvc.servlet.path}")
    private String basePath;

    /**
     * Bean for SecurityFilterChain.
     *
     * <p>
     * This bean configure almost all the security properties of the application
     * provided
     * by Spring Security.
     * </p>
     *
     * <p>
     * In this case, the SecurityFilterChain is configured with the properties
     * defined in the application.properties file.
     * </p>
     *
     * @param httpSecurity The HttpSecurity object
     * @return A new instance of SecurityFilterChain
     * @throws Exception If an error occurs
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.NEVER))
                .authorizeHttpRequests(
                        authRequest -> {
                            authRequest
                                    .requestMatchers(securityProperties.getAllowedPublicRoutes()).permitAll()
                                    .anyRequest().authenticated();
                        })
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Bean for AuthenticationManager.
     *
     * @param authenticationConfiguration The AuthenticationConfiguration object.
     * @return A new instance of AuthenticationManager.
     * @throws Exception If an error occurs
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean for CorsConfigurationSource.
     *
     * @return A new instance of CorsConfigurationSource
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(securityProperties.getAllowedOrigins()));
        config.setAllowedMethods(Arrays.asList(securityProperties.getAllowedMethods()));
        config.setAllowedHeaders(Arrays.asList(securityProperties.getAllowedHeaders()));
        config.setAllowCredentials(securityProperties.isAllowCredentials());

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Bean for PasswordEncoder.
     *
     * <p>
     * This bean configure the password encoder of the application provided by
     * Spring Security. In this case, the password encoder is BCryptPasswordEncoder.
     * </p>
     *
     * @return A new instance of PasswordEncoder
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder;
    }
}
