package ro.tuc.ds2020.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));  // Extrage rolul din token
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean validateToken(String token) {
        return !extractExpiration(token).before(new Date());
    }

    private <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    private Claims extractAllClaims(String token) {
        String tokenWithoutBearer = token.replace("Bearer ", "");
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(tokenWithoutBearer)
                .getBody();
        System.out.println("JWT Claims: " + claims);
        return claims;
    }

    @FunctionalInterface
    public interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
    public String extractUserId(String token) {
        String tokenWithoutBearer = token.replace("Bearer ", "");
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(tokenWithoutBearer).getBody();
        return claims.get("userId", String.class); // Replace "userId" with the correct claim key
    }

}
