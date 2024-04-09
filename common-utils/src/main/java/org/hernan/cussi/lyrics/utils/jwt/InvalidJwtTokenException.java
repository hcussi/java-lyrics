package org.hernan.cussi.lyrics.utils.jwt;

import io.jsonwebtoken.JwtException;

public class InvalidJwtTokenException extends JwtException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
