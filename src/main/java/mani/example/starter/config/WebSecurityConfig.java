package mani.example.starter.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {
    private static final String[] WHITELIST={
        "/",
        "/login",
        "/register",
        "/db-console/**",
        "/css/**",
        "/fonts/**",
        "/images/**",
        "/js/**"

    };

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Authorization configurations
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(WHITELIST).permitAll() // Allow listed URLs
                                .anyRequest().authenticated()          // Require auth for others
                )
                // Disable CSRF for H2 console
                .csrf(csrf -> csrf.ignoringRequestMatchers("/db-console/**"))
                // Allow frames for H2 console
                .headers(headers -> headers.frameOptions().sameOrigin())
                // Login configuration
                .formLogin(form -> form
                                .loginPage("/login")                      // Custom login page
                                .loginProcessingUrl("/login")             // Login processing URL
                                .usernameParameter("email")               // Username parameter
                                .passwordParameter("password")            // Password parameter
                                .defaultSuccessUrl("/register", true)             // Redirect after successful login
                                .failureUrl("/login?error")               // Redirect after failed login
                                .permitAll()                              // Allow access to login page
                )
                // Logout configuration
                .logout(logout -> logout
                                .logoutUrl("/logout")                     // Logout URL
                                .logoutSuccessUrl("/logout?success")      // Redirect after successful logout
                                .permitAll()                              // Allow access to logout
                )
                // HTTP Basic Authentication (optional)
                .httpBasic(withDefaults());

        return http.build();
    }
    
}
