//package com.apigatewayservice.config;
//
//import com.apigatewayservice.auth.security.JwtFilter;
//import com.apigatewayservice.auth.security.TokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@RequiredArgsConstructor
//@EnableWebSecurity
//public class SecurityConfig{
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().
//                requestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//                .requestMatchers(new AntPathRequestMatcher( "/favicon.ico"))
//                .requestMatchers(new AntPathRequestMatcher( "/css/**"))
//                .requestMatchers(new AntPathRequestMatcher( "/js/**"))
//                .requestMatchers(new AntPathRequestMatcher( "/img/**"))
//                .requestMatchers(new AntPathRequestMatcher( "/lib/**"));
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http	.csrf(AbstractHttpConfigurer::disable)
//                .httpBasic(AbstractHttpConfigurer::disable)
//                .formLogin(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests((authorize) -> authorize
//                        .requestMatchers("/user-service/api/v1/auth/**", "/actuator/**").permitAll()
//                        .anyRequest().authenticated())
//                .logout((logout) -> logout
//                        .logoutSuccessUrl("/login")
//                        .invalidateHttpSession(true))
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//        return http.build();
//    }
//}