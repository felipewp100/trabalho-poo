package br.edu.atitus.atitusound.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.edu.atitus.atitusound.components.AuthTokenFilter;

@Configuration
public class ConfigSecurity {

	
	private final AuthTokenFilter auth;
	
	public ConfigSecurity(AuthTokenFilter auth) {
		super();
		this.auth = auth;
	}



	@Bean
	public SecurityFilterChain getFilterChain(HttpSecurity http) throws Exception{
		http
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**","/swagger-ui*","swagger-ui/**", "/v3/api-docs/**").permitAll()
				.anyRequest().authenticated())
		.addFilterBefore(auth, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
