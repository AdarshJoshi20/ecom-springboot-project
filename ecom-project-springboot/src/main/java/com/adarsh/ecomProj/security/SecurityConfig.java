package com.adarsh.ecomProj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


//----------------------------------------------------------------------------

//This class is used to configure spring app such that it allows
//1. requests from React (localhost: 5173), without it the home page doesn't load
// and i am unable to do any CRUD operations because of CORS error. 
//react is in port 5173 and SB in port 8080

//2. this class also disables CSRF temporarily (for API testing: done using Postman)

// 3. permits all requests (so that the app works fine as earlier)

//----------------------------------------------------------------------------


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		 http
         .csrf(csrf -> csrf.disable())
         .cors(Customizer.withDefaults())
         .authorizeHttpRequests(auth ->
             auth.anyRequest().permitAll());

     return http.build();
	}
}
