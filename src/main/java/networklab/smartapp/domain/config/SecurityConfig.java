package networklab.smartapp.domain.config;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
 * @author 태경 2022-07-07
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .invalidSessionUrl("/auth/invalid-session")
                .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                    .expiredUrl("/auth/expired-session");

        http
                .formLogin()
                    .loginPage("/auth/login")
                    .defaultSuccessUrl("/main")
                    .failureUrl("/auth/login?error=true")
                    .permitAll()
                    .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/auth/login");

        http
                .httpBasic().disable()
                .csrf().disable()
                .cors();

        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                .antMatchers("/auth/**", "/css/**", "/photo/**").permitAll()
                .anyRequest().hasAuthority("ROLE_USER");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
}
