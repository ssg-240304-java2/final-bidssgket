package com.ssg.bidssgket.global.config;


import com.ssg.bidssgket.user.domain.member.api.googleLogin.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/admin/**"),
                        new AntPathRequestMatcher("/user/**"),
                        new AntPathRequestMatcher("/error"),
                        new AntPathRequestMatcher("/error"),
                        new AntPathRequestMatcher("/favicon.ico"),
                        new AntPathRequestMatcher("/image_make_light"),
                        new AntPathRequestMatcher("/no-image-square"),
                        new AntPathRequestMatcher("/valid.js")
                );    //static관련 핸들러 메소드에서 필터링 제외시킴.
    }

    //                .antMatchers("/api/users/**").permitAll()
//                .antMatchers("/api/issue").permitAll()
//                .antMatchers("/admin/**").permitAll()
//                .antMatchers("/favicon.ico").permitAll()
//                .antMatchers("/swagger-ui/**").permitAll()
//                .antMatchers("/api-docs/**").permitAll()
//                .antMatchers("/error/**").permitAll()
    private final CustomOAuth2UserService customOAuth2UserService;

    public WebSecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .headers(headerConfig -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/main", "/login", "/oauth2/**").permitAll()
                                .requestMatchers("/auction/**").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .anyRequest().authenticated()
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/main")
                )
                .oauth2Login(oauth ->
                        oauth
                                .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                                .loginPage("/login")
                                .defaultSuccessUrl("/main",true)
                );
        return http.build();
    }
}
