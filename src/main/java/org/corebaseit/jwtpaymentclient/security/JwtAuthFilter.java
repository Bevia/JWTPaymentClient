package org.corebaseit.jwtpaymentclient.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.corebaseit.jwtpaymentclient.service.JwtService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;



@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String path = request.getRequestURI();
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.debug("JWT filter on {} — Authorization: {}", path, authHeader);

        if (authHeader != null && authHeader.regionMatches(true, 0, "Bearer ", 0, 7) && authHeader.length() > 7) {
            String token = authHeader.substring(7).trim();
            try {
                String subject = jwtService.getSubject(token);
                var auth = new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.debug("JWT OK, subject={}", subject);
            } catch (Exception e) {
                log.warn("JWT validation failed: {}", e.getMessage());
            }
        } else if (authHeader != null) {
            log.warn("Authorization header present but not Bearer: {}", authHeader);
        }

        chain.doFilter(request, response);
    }
}