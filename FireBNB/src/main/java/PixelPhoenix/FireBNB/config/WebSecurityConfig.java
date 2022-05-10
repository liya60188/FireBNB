package PixelPhoenix.FireBNB.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import PixelPhoenix.FireBNB.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	
	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	    	
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Pages that do not require login
		http.authorizeRequests()
			.antMatchers("/", "/registration").permitAll()
			//Pages for admin only
			.antMatchers("/users").hasAnyAuthority("admin")
			//Pages for admin and users
			.antMatchers("/profile").access("hasRole('user') or hasRole('admin')")
			.anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/registration")
			.loginProcessingUrl("/login")
			.usernameParameter("email")
			.passwordParameter("password")
			.defaultSuccessUrl("/profile")
			.and()
			.logout().logoutSuccessUrl("/");
		
	}
	
	
}