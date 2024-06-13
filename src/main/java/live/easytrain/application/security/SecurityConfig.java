package live.easytrain.application.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@Configuration
@EnableJdbcHttpSession
public class SecurityConfig {
    private UserDetailsService userDetailsService;

    public void configurationGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        String[] staticResources = {"/images/**", "/css/**", "/scripts/**"};

        httpSecurity.authorizeHttpRequests(
                        customizer -> customizer
                                .requestMatchers(staticResources).permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticate_user")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .rememberMe((rememberMe) -> rememberMe
                        .rememberMeServices(rememberMeServices())
                )
                .logout()
                .deleteCookies("SESSION")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");

        return httpSecurity.build();
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices =
                new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }
}