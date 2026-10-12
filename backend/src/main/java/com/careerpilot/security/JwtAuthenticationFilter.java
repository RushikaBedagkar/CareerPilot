package com.careerpilot.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(
            JwtService jwtService) {

        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader =
                request.getHeader("Authorization");

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token =
                authHeader.substring(7).trim();

        // Prevent malformed token caused by accidental quotes/spaces
        if (token.isEmpty() ||
                token.contains(" ") ||
                token.startsWith("\"") ||
                token.endsWith("\"")) {

            filterChain.doFilter(request, response);
            return;
        }

        try {

            String email =
                    jwtService.extractUsername(token);

            String role =
                    jwtService.extractRole(token);

            if (email != null &&
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication() == null) {

                if (jwtService.isTokenValid(
                        token,
                        email)) {

                    String authority =
                            "ROLE_" + role;

                    UsernamePasswordAuthenticationToken
                            authentication =
                            new UsernamePasswordAuthenticationToken(
                                    email,
                                    null,
                                    Collections.singletonList(
                                            new SimpleGrantedAuthority(
                                                    authority
                                            )
                                    )
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(
                                    authentication
                            );
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "JWT ERROR: "
                            + e.getClass().getName()
            );

            System.out.println(
                    "JWT ERROR MESSAGE: "
                            + e.getMessage()
            );
        }

        filterChain.doFilter(request, response);
    }
}