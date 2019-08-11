package programming.blog.security;

import java.util.Arrays;

import org.springframework.beans.ConfigurablePropertyAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import programming.blog.service.UserService;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public WebSecurity(UserService userDetailsService,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	
	
	@Override
 	protected void configure(HttpSecurity http) throws Exception {
 		http.cors()
 		.and()
 		.csrf().disable()
 		.authorizeRequests()
 		//.antMatchers(HttpMethod.OPTIONS,"/**").permitAll()
 		//.antMatchers(HttpMethod.POST,"/users/login").permitAll()
 		.antMatchers(HttpMethod.GET,"/**").permitAll()// Allow all GET requests to go unauthenticated
 		.antMatchers(HttpMethod.POST,SecurityConstants.SIGN_UP_URL).permitAll()
 		//.antMatchers("/blogs/posts").hasRole("ROLE_ADMIN")
 		//.antMatchers("/blogs/posts").hasAuthority("ADMIN")
 		.anyRequest().authenticated()
 		.and()
 		.addFilter(getAuthenticationFilter())
 		//.formLogin().permitAll()
 		.addFilter(new AuthorizationFilter(authenticationManager()))
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
 	}

 	@Override
 	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
// 		auth
// 		// enable in memory based authentication with a user named "user" and "admin"
// 		.inMemoryAuthentication().withUser("user").password("password").roles("USER")
// 				.and().withUser("admin").password("password").roles("USER", "ADMIN");
 		
 		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
 	}
 	
 	
//	TO OVERRIDE THE DEFAULT LOGIN URL , WE NEED TO CREATE A NEW METHOD ABELOW
    public AuthenticationFilter getAuthenticationFilter()throws Exception{
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/users/login");
        return authenticationFilter;
    }
    
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders((Arrays.asList("*")));
		configuration.setAllowCredentials(true);
		configuration.setExposedHeaders(Arrays.asList("USER_ID",
				"Authorization",
				"USER_EMAIL",
				"USER_NAME",
				"TOKEN_EXPIRATION"));
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	

}
