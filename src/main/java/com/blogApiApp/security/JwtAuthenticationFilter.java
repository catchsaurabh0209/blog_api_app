package com.blogApiApp.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// GET Token
		String requestToken= request.getHeader("Authorization");
		
		// Request Token will be in the form Bearer 23457sdkaka
		System.out.println(requestToken);
		
		String username=null;
		String token=null;
		if(requestToken != null && requestToken.startsWith("Bearer"))
		{
			token=requestToken.substring(7);
			
			try
			{
				username=jwtTokenHelper.getUsernameFromToken(token);
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Unable to get JWT Token");
			}
			catch(ExpiredJwtException ex)
			{
				System.out.println("JWT token got expired");
			}
			catch(MalformedJwtException ex)
			{
				System.out.println("Invalid JWT token");
			}
		}
		else
		{
			System.out.println("JWT token does starts with BEARER");
		}
		
		// Once we get the token
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			UserDetails userDetails= this.userDetailsService.loadUserByUsername(username);
			
			if(this.jwtTokenHelper.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken= new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else
			{
				System.out.println("Invalid JWT Token");
			}
		}
		else 
		{
			System.out.println("Either userName or Security context is not NULL");
		}
		
		filterChain.doFilter(request, response);
	}

}
