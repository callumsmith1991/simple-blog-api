package com.example.simple_blog_rest_api.middleware;

import com.example.simple_blog_rest_api.config.auth.JwtUtil;
import com.example.simple_blog_rest_api.models.User;
import com.example.simple_blog_rest_api.repositorys.UserRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AdminUserFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthTokenFilter authTokenFilter;

    @Autowired
    UserRepository userRepository;

    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, java.io.IOException {

        String jwt = authTokenFilter.parseJwt(request);

        if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
            String email = jwtUtil.getEmailFromToken(jwt);

            boolean isAdmin = userRepository.isAdmin(email);

            if(isAdmin) {
                filterChain.doFilter(request, response); // ✅ Proceed
                return;
            }

        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Admin access required");

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/api/admin/");
    }



}
