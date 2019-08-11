package programming.blog.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import programming.blog.SpringApplicationContext;
import programming.blog.dto.UserDto;
import programming.blog.request.UserLoginRequestModel;
import programming.blog.serviceimpl.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    
    private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    public AuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
     HttpServletResponse response) throws AuthenticationException {
        try {
        	//logger.info("request is ---------------{}",request);
        	//String name = request.getParameter("password");
        	//logger.info("name is {}",name);
            
        	UserLoginRequestModel creds = 
        			new ObjectMapper().readValue(request.getInputStream(),UserLoginRequestModel.class);
            
            
         List<GrantedAuthority>roles = new ArrayList<>();
   		 GrantedAuthority adminRole = new SimpleGrantedAuthority("ADMIN");
   		 GrantedAuthority userRole = new SimpleGrantedAuthority("USER");
   		 if(creds.getEmail().equalsIgnoreCase("shanker9994@gmail.com")) {
   			
   			roles.add(adminRole);
   			roles.add(userRole);

   			
   		}else {
   			roles.add(userRole);
   			
   		}

            return  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            roles
                    )
            );
        }catch (IOException ex){
            throw new RuntimeException();
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        //String userName = authResult.getPrincipal().toString();
        String userName = ((User)authResult.getPrincipal()).getUsername();
        //Collection<? extends GrantedAuthority> x = authResult.getAuthorities();
//        Iterator<? extends GrantedAuthority> itr = x.iterator();
//        while(itr.hasNext()) {
//        	GrantedAuthority l = itr.next();
//        	l.getAuthority();
//        	logger.info("Role is -------------------{}",l.getAuthority());
//        }
        
        //generate json webservice token
        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET )
                .compact();

        //create object to get the userId
        UserServiceImpl userService =(UserServiceImpl) SpringApplicationContext.getBean("userServiceImpl");
        UserDto userDto = userService.findUserByEmail(userName);

        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+token);
        response.addHeader("USER_ID",userDto.getUserId());
        response.addHeader("USER_EMAIL", userName);
        response.addHeader("USER_NAME", userDto.getUserName());
        response.addHeader("TOKEN_EXPIRATION", String.valueOf(SecurityConstants.EXPIRATION_TIME));
        //super.successfulAuthentication(request, response, chain, authResult);
    }
    /*
    @Override 
    protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();

		if (logger.isDebugEnabled()) {
			logger.debug("Authentication request failed: " + failed.toString(), failed);
			logger.debug("Updated SecurityContextHolder to contain null Authentication");
			//logger.debug("Delegating to authentication failure handler " + failureHandler);
		}
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put(
          "timestamp", 
          Calendar.getInstance().getTime());
        data.put(
          "exception", 
          failed.getMessage());
 
		//rememberMeServices.loginFail(request, response);

		//failureHandler.onAuthenticationFailure(request, response, failed);
	}*/
}
