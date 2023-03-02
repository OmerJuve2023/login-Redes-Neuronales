package com.neuronales.login.jwt;

import com.neuronales.login.services.UserDetailServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    private JwtProvider jwtProvider;

    private UserDetailServiceImpl userDetailService;

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToker(request);
            if (token != null && jwtProvider.validateToken(token)) {
                String username = jwtProvider.getUsernameFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    private String getToker(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, cookieName);
        return cookie != null ? cookie.getValue() : null;
    }
}
