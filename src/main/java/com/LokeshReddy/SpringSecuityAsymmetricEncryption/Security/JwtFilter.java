package com.LokeshReddy.SpringSecuityAsymmetricEncryption.Security;

import com.LokeshReddy.SpringSecuityAsymmetricEncryption.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor

public class JwtFilter extends OncePerRequestFilter {

    private  UserService userService;
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(
                                    @NotNull
                                    HttpServletRequest request,
                                    @NotNull
                                    HttpServletResponse response,
                                    @NotNull
                                    FilterChain filterChain) throws ServletException, IOException {

        if(request.getServletPath().contains("/api/v1/auth")){
            filterChain.doFilter(request,response);
            return;
        }
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String username;
        if(authHeader==null||authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        jwt=authHeader.substring(7);
        username=this.jwtService.extractUsername(jwt);

        if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
            final UserDetails userDetails= userService.loadUserByUsername(username);
            if(this.jwtService.isTokenValid(jwt,username)){
                final UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    }
}
