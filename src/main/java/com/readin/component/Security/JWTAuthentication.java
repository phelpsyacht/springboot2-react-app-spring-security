package com.readin.component.Security;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthentication {
	static final long EXPIRATIONTIME = 432_000_000;  
	static final String SECRET = "B@ssw00w";            
	static final String TOKEN_PREFIX = "Kolfwy";        
	static final String HEADER_STRING = "Authorization";

	public static void addAuthentication(HttpServletResponse response, String username) {
		String JWT = Jwts.builder()
                .claim("authorities", "ROLE_USER")
                .setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println(fillResultString(0, "", JWT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
	    if (token != null) {
                Claims claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
					.getBody();
		    String user = claims.getSubject();
		    List<GrantedAuthority> authorities =  AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
		    if (user != null)
		    	return new UsernamePasswordAuthenticationToken(user, null, authorities);
		    else
            	return null;
	    }
		return null;
    }

    public static String tokenStr(String username) {
		String jwt = Jwts.builder()
                .claim("authorities", "ROLE_USER")
                .setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
		return jwt;
	}
    
    public static String fillResultString(Integer status, String message, Object result){
        JSONObject jsonObject = new JSONObject(){{
            put("status", status);
            put("message", message);
            put("result", result);
        }};

        return jsonObject.toString();
    }
}
