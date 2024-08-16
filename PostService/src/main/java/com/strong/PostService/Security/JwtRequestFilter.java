package com.strong.PostService.Security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.strong.PostService.Repository.AuthorServiceClient;
import com.strong.PostService.Utils.JwtValidationResponse;

import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private AuthorServiceClient authorServiceFeignClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String jwt = extractJwtFromRequest(request);

        if (StringUtils.hasText(jwt)) {
            try {
                ResponseEntity<JwtValidationResponse> authResponse = authorServiceFeignClient.validateToken(jwt,
                        "Bearer " + jwt);

                if (authResponse.getStatusCode().is2xxSuccessful()) {
                    JwtValidationResponse validationResponse = authResponse.getBody();

                    List<GrantedAuthority> authorities = validationResponse.getRoles().stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            validationResponse.getUsername(), null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (FeignException e) {
                // Log or handle Feign exception
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
