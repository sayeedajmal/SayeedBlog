package com.strong.AuthorService.Security;

import java.io.IOException;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.strong.AuthorService.Service.AuthorService;
import com.strong.AuthorService.Utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JwtRequestFilter is a Spring Security filter that intercepts HTTP requests to
 * handle JWT-based authentication. It extends OncePerRequestFilter to ensure
 * that it
 * processes each request only once per request cycle.
 * <p>
 * This filter extracts the JWT from the "Authorization" header, validates it,
 * and sets the
 * authentication information in the SecurityContext if the token is valid.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final AuthorService authorService;
    private final JwtUtil jwtUtil;

    /**
     * Constructor for JwtRequestFilter.
     *
     * @param authorService the service used to load user details.
     * @param jwtUtil       the utility class for handling JWT operations.
     */
    public JwtRequestFilter(@Lazy AuthorService authorService, JwtUtil jwtUtil) {
        this.authorService = authorService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Filters requests to handle JWT-based authentication.
     * 
     * <p>
     * This method extracts the JWT from the "Authorization" header, validates it,
     * and sets the authentication information in the SecurityContext if the token
     * is valid.
     * 
     * @param request  the HTTP request containing the JWT in the "Authorization"
     *                 header.
     * @param response the HTTP response.
     * @param chain    the filter chain to continue the request processing.
     * @throws ServletException if an error occurs during request processing.
     * @throws IOException      if an I/O error occurs during request processing.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            try {
                String username = jwtUtil.extractUserEmail(jwt);
                UserDetails userDetails = authorService.loadUserByUsername(username);

                if (userDetails != null && jwtUtil.isValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            } catch (Exception e) {
                // Handle the exception and send an unauthorized response
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getLocalizedMessage());
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
