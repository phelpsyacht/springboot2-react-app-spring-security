package com.readin.robot.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.readin.component.CustomerUsernamePasswordAuthenticationFilter;
import com.readin.component.Security.JWTAuthentication;
import com.readin.component.Security.Credential;

import org.json.JSONObject;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    @Qualifier("CustomUserServiceImpl")
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder()); 

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	.addFilterBefore(new LoginFilter("/login", authenticationManager()),
        		CustomerUsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(new JWTFilter(),
				CustomerUsernamePasswordAuthenticationFilter.class).authorizeRequests()
	.antMatchers("/",  "/regin").permitAll()
        .anyRequest().authenticated(); 
    }
    
    @Bean
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
    	return super.authenticationManagerBean();
    }
    
    protected Filter getSuccessFilter() throws Exception {
        CustomerUsernamePasswordAuthenticationFilter authenticationFilter = new CustomerUsernamePasswordAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationFilter;
    }
    
    public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    	public LoginFilter(String url, AuthenticationManager authManager) {
            super(new AntPathRequestMatcher(url));
            setAuthenticationManager(authManager);
    	}

    	@Override
    	public Authentication attemptAuthentication(
    			HttpServletRequest req, HttpServletResponse res)
    			throws AuthenticationException, IOException, ServletException {

    		Credential cred = new ObjectMapper().readValue(req.getInputStream(), Credential.class);

            return getAuthenticationManager().authenticate(
    				new UsernamePasswordAuthenticationToken(
    						cred.getName(),
    						cred.getPassword()
    				)
    		);
    	}

    	@Override
    	protected void successfulAuthentication(
    			HttpServletRequest req,
    			HttpServletResponse res, FilterChain chain,
    			Authentication auth) throws IOException, ServletException {
    		JWTAuthentication.addAuthentication(res, auth.getName());
    	}

        @Override
        protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println(JWTAuthentication.fillResultString(500, "Internal Server Wrong!", JSONObject.NULL));
        }
    }

    public class JWTFilter extends GenericFilterBean {
        @Override
        public void doFilter(ServletRequest request,
                             ServletResponse response,
                             FilterChain filterChain)
                throws IOException, ServletException {
            Authentication authentication = JWTAuthentication
                    .getAuthentication((HttpServletRequest)request);

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            filterChain.doFilter(request,response);
        }
    }
    
}
