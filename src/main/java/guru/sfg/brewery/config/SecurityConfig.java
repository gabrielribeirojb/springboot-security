package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by jt on 6/13/20.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
                http
                .authorizeRequests(authorize -> {
                    authorize.antMatchers("/", "/webjars/**", "/login", "/resources/**").permitAll()
                    .antMatchers( "/beers/find", "/beers*").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                    .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll();
                } )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }
    
    @Bean
	PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    	auth.inMemoryAuthentication()
    	.withUser("spring")
    	.password("{SSHA}/q7mq5hpdPb4osnVJ08IdDfGyyEL4WFH0bEePg==")
    	.roles("ADMIN")
    	.and()
    	.withUser("user")
    	.password("5f94480e50b581014a05a107581a9ccdf7fcf0328c99f91856335c6bef2028362b0136cf5c9b1e92")
    	.roles("USER")
    	.and()
    	.withUser("scott")
    	.password("tiger")
    	.roles("COSTUMER");
    }
    
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//    	@SuppressWarnings("deprecation")
//		UserDetails admin = User.withDefaultPasswordEncoder()
//    			.username("spring")
//    			.password("guru")
//    			.roles("ADMIN")
//    			.build();
//    	
//    	 @SuppressWarnings("deprecation")
//		UserDetails user = User.withDefaultPasswordEncoder()
//                 .username("user")
//                 .password("password")
//                 .roles("USER")
//                 .build();
//
//         return new InMemoryUserDetailsManager(admin, user);
//    }
}