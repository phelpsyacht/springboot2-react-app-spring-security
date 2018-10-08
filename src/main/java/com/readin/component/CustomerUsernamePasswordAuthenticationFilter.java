package com.readin.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomerUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	public static final String USERNAME = "name";    
	public static final String PASSWORD = "password";
	@Override    
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {    
        if (!request.getMethod().equals("POST")) {    
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());    
        }    
        
        String name = obtainUsername(request);    
        String password = obtainPassword(request);    
        System.out.println(password);    
         
        name = name.trim();
        if (name == null || password == null ){                 
            throw new AuthenticationServiceException("用户名密码错误！");     
        }       
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(name, password);
        System.out.println(authRequest);
       
        setDetails(request, authRequest);
       
        System.out.println(getAuthenticationManager());
          
        return getAuthenticationManager().authenticate(authRequest);    
    }    	
    @Override    
    protected String obtainUsername(HttpServletRequest request) {    
        Object obj = request.getParameter(USERNAME);    
        return null == obj ? "" : obj.toString();    
    }    
  
    @Override    
    protected String obtainPassword(HttpServletRequest request) {
        Object obj = request.getParameter(PASSWORD);    
        return null == obj ? "" : obj.toString();    
    }    
        
}
