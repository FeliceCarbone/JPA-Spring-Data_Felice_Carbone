package it.aulab.progetto_blog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final static String cspDirectives = "default-src 'self' ; img-src 'self'; script-src 'self' cdn.jsdelivr.net 'unsafe-inline'; style-src 'self' cdn.jsdelivr.net cdnjs.cloudflare.com ; font-src cdnjs.cloudflare.com";

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userManager(){
        UserBuilder user = User.withUsername("user").password(encoder().encode("12345678"));
        UserBuilder admin = User.withUsername("admin").password(encoder().encode("admin12345678"));
        return new InMemoryUserDetailsManager(user.build(), admin.build());
    }

    @Bean
    public SecurityFilterChain confiSecurityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(
            (authorize) -> authorize.requestMatchers("/api/**").permitAll().anyRequest().authenticated()).
            formLogin((formLogin) -> formLogin.loginPage("/login")
            .defaultSuccessUrl("/authors", true)
            .permitAll())
            .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/"))
            .csrf((csrf) -> csrf.ignoringRequestMatchers("/api/**"))
            .headers((headers) -> headers.xssProtection(Customizer.withDefaults())
                .contentSecurityPolicy(Customizer.withDefaults()).contentSecurityPolicy((csp) -> csp.policyDirectives(cspDirectives)));
        
        return http.build();
    }
}
