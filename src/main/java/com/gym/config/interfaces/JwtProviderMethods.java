package com.gym.config.interfaces;

import org.springframework.security.core.Authentication;

public interface JwtProviderMethods {
    String generateToken(Authentication username);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
