package com.meetings.conferent.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/meetings/**").permitAll().anyRequest().anonymous();
		http.csrf().disable().authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic();
		// http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated().and().authorizeRequests().antMatchers("/meetings").permitAll().anyRequest().authenticated().and().authorizeRequests().antMatchers("/rooms").permitAll().anyRequest().authenticated();
		/*
		 * http.authorizeRequests().antMatchers("/admin/**").access(
		 * "hasRole('ROLE_ADMIN')").and().formLogin()
		 * .loginPage("/login").failureUrl("/login?error").usernameParameter("username")
		 * .passwordParameter("password").and().logout().logoutSuccessUrl(
		 * "/login?logout").and().csrf().and()
		 * .exceptionHandling().accessDeniedPage("/403");
		 */
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}
