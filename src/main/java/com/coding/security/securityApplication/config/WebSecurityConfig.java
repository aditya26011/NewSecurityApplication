package com.coding.security.securityApplication.config;



import com.coding.security.securityApplication.entity.enums.Permissions;
import com.coding.security.securityApplication.filters.JwtAuthFilter;
import com.coding.security.securityApplication.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.coding.security.securityApplication.entity.enums.Permissions.*;
import static com.coding.security.securityApplication.entity.enums.Role.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    private static final String[] publicRoutes={
            "/auth/**","/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                //Any request that we make will be authenticated now
                                .authorizeHttpRequests(auth->
                                auth
                                        .requestMatchers(publicRoutes).permitAll()
                                        .requestMatchers(HttpMethod.GET,"/posts/**").permitAll()
                                        .requestMatchers(HttpMethod.POST,"/posts/**").hasAnyRole(ADMIN.name(), CREATOR.name())
                                        .requestMatchers(HttpMethod.POST,"/posts/**")
                                        .hasAuthority(POST_CREATE.name())

                                        .requestMatchers(HttpMethod.PUT,"/posts/**")
                                        .hasAuthority(POST_UPDATE.name())
                                        .requestMatchers(HttpMethod.DELETE,"/post/**")
                                        .hasAuthority(POST_DELETE.name())
                                .anyRequest().authenticated())
                .csrf(CsrfConfig->CsrfConfig.disable())
                .sessionManagement(sessionConfig->
                        sessionConfig.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                //Adding our own jwt filter before username and password filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                //adding Oauth2
                .oauth2Login(oauth2Config->oauth2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );


        ;


//                .formLogin(Customizer.withDefaults());//will go to /login path which is default.


        return httpSecurity.build();
    }


    //Used for Login to check the authentication
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    //Creating users
//    @Bean
//    UserDetailsService myInMemoryUserDetailService(){
//      UserDetails normalUser=User
//              .withUsername("Aditya")
//              .password(passwordEncoder().encode("Aditya123")).
//              roles("USER")
//              .build();
//
//        UserDetails adminUser=User
//                .withUsername("Admin")
//                .password(passwordEncoder().encode("Admin123"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(normalUser,adminUser);
//    }



}
