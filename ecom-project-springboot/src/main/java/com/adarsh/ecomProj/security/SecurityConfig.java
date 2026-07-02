package com.adarsh.ecomProj.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


//----------------------------Description of imported objects------------------------------------------------
// 1. Imports @Bean annotation, which tells Spring to create the object and store it in 
//spring container

// 2. Imports @Configuration annotation, which makes the class a Config class and it tells
// spring how to create objects and configure them.

// 3. used for: .cors(Customizer.withDefaults()) -> it tells spring to use spring security's
// default CORS configuration

// 4. Imports HttpSecurity class which lets us define rules like: login, logout, JWT, 
// who can access URLs etc. Everything is configured through this object.

//		Example:
		//HttpSecurity
//		↓
//		Configure Security Rules
//		↓
//		Spring Security


// 5. Imports @EnableWeb Security : it loads all security-related beans, without this
// the custom security config would'nt be activated

// 6. Imports the complete chain of security filters (like JWT filters, auth filter, cors filter etc.)
// every incoming request passes through this chain. 
//			Browser
//			│
//			▼
//			Security Filter Chain
//			│
//			▼
//			Controller

//----------------------------------------------------------------------------

// @Bean: It tells Spring: Whatever object this method returns, 
//store it inside the Spring IoC Container.
//Here it returns : SecurityFilterChain . 
//So Spring stores one SecurityFilterChain object.

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
	// this method returns configured security filter chain
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		// http is builder object of HttpSecurity Class and it applies following config
		// to the security configuration.
		 http
         .csrf(csrf -> csrf.disable()) // disables CSRF token (Cross Site Request Forgery token) bcoz no browser session-based auth is being used.
         .cors(Customizer.withDefaults()) // enables CORS so that fronend (port 5173) can request to backend (port 8080)
         
         // this lambda function permits all types of requests (get, put, post etc) to be
         // sent to the server by any kind of user (admin, normal user etc)
         // TO BE UPDATED later to restrict access based on roles or authorities.
         .authorizeHttpRequests(auth ->
             auth.anyRequest().permitAll());
		 

		 http.httpBasic(Customizer.withDefaults());
		 
		 // the configured CSRF, CORS, authorization is stored by spring into a SecurityFilterChain object
//		 which is then returned (this 'http')
		 return http.build();
     
//     Diagram:  
//    Spring Boot
//         │
//    Creates HttpSecurity
//         │
//    Calls securityFilterChain()
//         │
//    You configure it
//         │
//    Returns SecurityFilterChain
	}
	
	//this method is used to create a new user while assigning its credentials 
	// and role. but this method is not to be used in actual prod projects
	//because here we are hard coding the values 
	// also the withDefaulPassowrdEncoder() method is deprecated.
//	@Bean
//	public UserDetailsService userDetailsService() {
//		
//		@SuppressWarnings("deprecation")
//		UserDetails user1 = User
//					.withDefaultPasswordEncoder()
//					.username("adarsh")
//					.password("a@123")
//					.roles("USER")
//					.build();
//		
//		return new InMemoryUserDetailsManager(user1);
//	}
}
