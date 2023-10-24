package com.kidsart.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

    @EnableWebSecurity
    @Configuration
    public class AdminConfiguration {

        private final UserDetailsService userDetailsService;

        @Autowired
        public AdminConfiguration(UserDetailsService userDetailsService) {
            this.userDetailsService = userDetailsService;
        }

        @Bean
        public static PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain  filterChain(HttpSecurity http) throws Exception {

            http .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests((authorize) ->
                            authorize.requestMatchers("/css/**","/imgs/**","/js/**","/fonts/**","/sass/**").permitAll()
                                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                    .anyRequest().authenticated()
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                            .invalidSessionUrl("/login")
                            .maximumSessions(1)
                            .maxSessionsPreventsLogin(false))
                    .formLogin(
                            form -> form
                                    .loginPage("/login")
                                    .loginProcessingUrl("/do-login")
                                    .defaultSuccessUrl("/")
                                    .failureUrl("/login?error")
                                    .permitAll()
                    )
                    .logout(
                            logout -> logout
                                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                    .invalidateHttpSession(true)
                                    .permitAll()
                    );
            return http.build();
        }

        @Autowired
        public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
        }
    }