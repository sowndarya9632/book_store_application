package com.book_store_application.filter;
import com.book_store_application.utility.TokenUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class JWTFilter extends HttpFilter {

    private TokenUtility tokenUtility;

    public JWTFilter(TokenUtility tokenUtility) {
        this.tokenUtility = tokenUtility;
    }


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix

            try {
                String role = tokenUtility.getRoleFromToken(token);
                long id = tokenUtility.getEmpIdFromToken(token);
                request.setAttribute("role", role);
                request.setAttribute("id",id);

                // Proceed with the filter chain
                chain.doFilter(request, response);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid token");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Authorization token missing or invalid");
        }
    }

    @Override
    public void init() throws ServletException {
        // Initialize the filter if needed
    }

    @Override
    public void destroy() {
        // Cleanup resources if needed
    }
}
