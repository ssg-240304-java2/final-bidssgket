package com.ssg.bidssgket.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        return authConfig.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


        http
                .httpBasic(HttpBasicConfigurer::disable)
//                .cors(corsConfigurationSource -> corsConfigurationSource.configurationSource(corsConfigurationSource()))
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                // disable session
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api-docs/**").permitAll()
                                .requestMatchers("/error/**").permitAll()
                                .anyRequest().authenticated()
                );
//                .exceptionHandling(handling -> handling.authenticationEntryPoint());


//        http.addFilterBefore(new AuthTokenFilter(jwtUtils, customUserDetailsService, tokenService), UsernamePasswordAuthenticationFilter.class); // 여기서 jwt 인증
//        http.addFilterBefore(jwtExceptionHandlerFilter, AuthTokenFilter.class); // jwt 예외처리 필터

        return http.build();

    }




}
