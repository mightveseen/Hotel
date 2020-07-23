package com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration;

import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.filter.AuthenticationFilter;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.filter.AuthorizationFilter;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.configuration.filter.TokenMapper;
import com.senlainc.gitcourses.javatraining.petushokvaliantsin.model.enumeration.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@PropertySource(value = "classpath:/properties/security.properties", ignoreResourceNotFound = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    @Value("${SECURITY_CONFIG.TOKEN_HEADER:Authorization}")
    private String tokenHeader;
    @Value("${SECURITY_CONFIG.TOKEN_PREFIX:Bearer}")
    private String tokenPrefix;
    @Value("${SECURITY_CONFIG.KEY:key}")
    private String secretKey;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/sign-up").anonymous()
                .antMatchers(HttpMethod.GET, "/orders/{id}").hasAnyRole(UserRole.GUEST.name(), UserRole.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/orders/{id}/attendances/{id}").hasAnyRole(UserRole.GUEST.name(), UserRole.ADMIN.name())
                .antMatchers("/orders", "/guests", "/rooms", "/attendances").hasRole(UserRole.ADMIN.name())
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .and().httpBasic()
                .and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilter(new AuthenticationFilter(authenticationManager(), tokenMapper()));
        http.addFilter(new AuthorizationFilter(authenticationManager(), tokenMapper()));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() {
        final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public TokenMapper tokenMapper() {
        return new TokenMapper(userDetailsService, tokenPrefix, secretKey, tokenHeader);
    }
}
