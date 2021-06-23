package guru.sfg.brewery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.SfgPasswordEncoderFactories;

/**
 * Created by jt on 6/13/20.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	 http.addFilterBefore(restHeaderAuthFilter(authenticationManager()),
                 UsernamePasswordAuthenticationFilter.class);
    	
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
		return SfgPasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
    	auth.inMemoryAuthentication()
    	.withUser("spring")
    	.password("{bcrypt}$2a$10$J/o0k0RGcgvUluGY78tLS.9erkBZGZhxphJNrzShbxnPHB2SYFZfG")
    	.roles("ADMIN")
    	.and()
    	.withUser("user")
    	.password("{sha256}76d44f4d0257ab9af30beaea064a04b7b73c131a7b3b5dfa8fe456bbcc2522647959ada971bdda65")
    	.roles("USER")
    	.and()
    	.withUser("scott")
    	.password("{bcrypt15}$2a$15$5qcEPcxvh8CpK8tXnqEtNOPmR1jGXlWWlHrB/yxbNNHm7OIa9CkPO")
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