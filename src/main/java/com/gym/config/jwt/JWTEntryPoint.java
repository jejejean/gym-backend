package com.gym.config.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JWTEntryPoint implements AuthenticationEntryPoint {

    private final static Logger logger = LoggerFactory.getLogger(JWTEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String errorMessage = "No autorizado";

        if (e.getMessage().contains("JWT expired")) {
            errorMessage = "Token expirado";
        } else if (e.getMessage().contains("Bad credentials")) {
            errorMessage = "Credenciales incorrectas";
        } else if (e.getMessage().contains("Full authentication is required")) {
            errorMessage = "Token no proporcionado o expirado";
        }

        logger.error("Error en el método commence: " + e.getMessage());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getOutputStream().println("{ \"error\": \"" + errorMessage + "\" }");
    }
}
