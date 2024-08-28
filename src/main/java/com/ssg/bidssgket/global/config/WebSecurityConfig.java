package com.ssg.bidssgket.global.config;


import com.ssg.bidssgket.user.domain.member.api.googleLogin.CustomAuthenticationFailureHandler;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.CustomOAuth2UserService;
import com.ssg.bidssgket.user.domain.member.api.googleLogin.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(
                        new AntPathRequestMatcher("/admin/**"),
                        new AntPathRequestMatcher("/user/**"),
                        new AntPathRequestMatcher("/error"),
                        new AntPathRequestMatcher("/favicon.ico"),
                        new AntPathRequestMatcher("/image_make_light"),
                        new AntPathRequestMatcher("/no-image-square"),
                        new AntPathRequestMatcher("/valid.js")
                );    //static관련 핸들러 메소드에서 필터링 제외시킴.
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig -> csrfConfig.disable())
                .headers(headerConfig -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/main", "/login", "/oauth2/**", "/chat", "/ws/**").permitAll()
                                .requestMatchers("/auction/**").permitAll()
                                .requestMatchers("/user/**").permitAll()
                                .requestMatchers("/wish/**").permitAll()
                                .anyRequest().authenticated()
                )
                .logout(logout ->
                        logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/")
                )
                .oauth2Login(oauth ->
                        oauth
                                .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                                .loginPage("/login")
                                .defaultSuccessUrl("/",true)
                ) .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .loginProcessingUrl("/memberLogin")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/", true)
                                .failureUrl("/login?error=true")
                                .failureHandler(new CustomAuthenticationFailureHandler())
//                                .failureHandler((request,response,excption) -> {
//                                    excption.printStackTrace(); //콘솔에 예외 출력가능한 코드
//                                    response.sendRedirect("/login?error=true");
//                                })
                                .permitAll()
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}