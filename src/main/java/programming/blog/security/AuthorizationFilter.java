package programming.blog.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;


public class AuthorizationFilter extends BasicAuthenticationFilter {

    private static Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if(header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request,response);

    }
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request){
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        logger.info("token is {}",token);
        if(token != null){

            token = token.replace(SecurityConstants.TOKEN_PREFIX,"");
            logger.info("token is {}",token);
            String user = Jwts.parser()
                    .setSigningKey( SecurityConstants.TOKEN_SECRET )
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();
            logger.info("user is  is {}",user);
            if(user != null){
       		 List<GrantedAuthority>roles = new ArrayList<>();
    		 GrantedAuthority adminRole = new SimpleGrantedAuthority("ADMIN");
    		 GrantedAuthority userRole = new SimpleGrantedAuthority("USER");
    		if(user.equalsIgnoreCase("shanker9994@gmail.com")) {
    			
    			roles.add(adminRole);
    			roles.add(userRole);

    			
    		}else {
    			roles.add(userRole);
    			
    		}
                return new UsernamePasswordAuthenticationToken(user,null,roles);
            }
            return null;
        }
    return  null;
    }

}
