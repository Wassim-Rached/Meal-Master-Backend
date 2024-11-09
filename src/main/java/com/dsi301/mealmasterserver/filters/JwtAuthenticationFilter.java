package com.dsi301.mealmasterserver.filters;

import com.dsi301.mealmasterserver.entities.Account;
import com.dsi301.mealmasterserver.repositories.AccountRepository;
import com.dsi301.mealmasterserver.services.JwtService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final AccountRepository userAccountRepository;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            try {
                // Validate the JWT
                String username = jwtService.extractUsername(jwtToken);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // Set the authentication in the context if it's not already set

                    Account userAccount = userAccountRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Account to authenticate not found"));

                    // not quite useful because no authorities are used in this app currently
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userAccount, null, userAccount.getAuthorities());


                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Save the authentication to the security context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }


            } catch (Exception e) {
                System.out.println("JWT Token validation failed: " + e.getMessage());
                // In case of failure. Make sure it's clear; so guarantee no authentication is set
                SecurityContextHolder.clearContext();
                response.sendError(401, "Invalid JWT token");
                return;
            }
        }

        chain.doFilter(request, response);
    }

}