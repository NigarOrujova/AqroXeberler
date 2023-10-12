package com.yelload.agro_news.security;

import com.google.common.collect.ImmutableList;
import com.yelload.agro_news.security.filter.AuthenticationFilter;
import com.yelload.agro_news.security.filter.ExceptionHandlerFilter;
import com.yelload.agro_news.security.filter.JWTAuthorizationFilter;
import com.yelload.agro_news.security.manager.CustomAuthenticationManager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationManager customAuthenticationManager;
    public SecurityConfig(CustomAuthenticationManager customAuthenticationManager) {
        this.customAuthenticationManager = customAuthenticationManager;
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

//    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        // Don't do this in production, use a proper list  of allowed origins
//        config.setAllowedOrigins(Collections.singletonList("*"));
//        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }

    /*
    *
   "http://localhost:8080",
   "http://127.0.0.1:8081",
   "http://localhost:8081",
   "http://164.90.216.81:8081",
   "http://127.0.0.1:5173",
   "http://localhost:5173",
   "http://127.0.0.1:3000",
   "http://localhost:3000"
    * */

//    @Bean
//    CorsFilter corsFilter() {
//        CorsFilter filter = new CorsFilter();
//        return filter;
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                 .headers().frameOptions().disable() //For H2 Console
                 .and()
                .authorizeRequests()
                .antMatchers("/h2/**").permitAll() // Access H2 database for development process
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH, "/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/image/**", "/news/**", "/settings/**",
                        "/account/**", "/tag/**").permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll() //Swagger
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOriginPatterns(ImmutableList.of("*"));
//        configuration.setAllowedMethods(ImmutableList.of("GET","POST","PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedHeaders(ImmutableList.of("*"));
//        configuration.setExposedHeaders(ImmutableList.of("*"));
//        configuration.setMaxAge(3600L);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }



    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(
//                "http://164.90.216.81:8081",
//                "http://127.0.0.1:8080",
//                "http://localhost:8080",
//                "http://127.0.0.1:8081",
//                "http://localhost:8081",
//                "http://127.0.0.1:5173",
//                "http://localhost:5173",
//                "http://127.0.0.1:3000",
//                "http://localhost:3000"
//        ));
//        configuration.setAllowedMethods(Arrays.asList(
//                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
//        ));
//        configuration.setAllowCredentials(true);

        //the below three lines will add the relevant CORS response headers
//        configuration.addAllowedOrigin("*");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");



        configuration.setAllowedOriginPatterns(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(ImmutableList.of("*"));
        configuration.setExposedHeaders(ImmutableList.of("*"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }





}