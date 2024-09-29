package com.ing.broker.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	
				        http.authorizeHttpRequests(
				        		(requests) 	-> 	requests.requestMatchers("/api/admin/**").hasRole("ADMIN") // Only ADMIN can access these endpoints
				        								.anyRequest().authenticated() // Other endpoints require authentication
					            ).httpBasic() // Enable Basic Authentication
					            .and()
					            .csrf().disable(); // Disable CSRF for simplicity in development
        
        return http.build();
        
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("adminpassword"))
            .roles("ADMIN")
            .build();

        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("userpassword"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
