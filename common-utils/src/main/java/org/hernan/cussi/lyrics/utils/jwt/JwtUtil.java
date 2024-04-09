package org.hernan.cussi.lyrics.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class JwtUtil {

  private final SecretKey secretKey;

  public JwtUtil(String apiSecretKey) {
    secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(apiSecretKey));
  }

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
  private Claims extractAllClaims(String token) throws JwtException {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(token).getPayload();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
      .claims(claims)
      .subject(subject)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
      .signWith(secretKey)
      .compact();
  }

  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

  public void validateToken(String token) {
    if (isTokenExpired(token)) {
      if(log.isWarnEnabled()) {
        log.warn("Invalid token has been provided");
      }
      throw new InvalidJwtTokenException("Invalid token has been provided");
    }
  }
}
