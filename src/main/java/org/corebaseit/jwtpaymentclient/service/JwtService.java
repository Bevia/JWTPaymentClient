package org.corebaseit.jwtpaymentclient.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class JwtService {

    private final String issuer;
    private final SecretKey key;

    public JwtService(
            @Value("${app.jwt.issuer}") String issuer,
            @Value("${app.jwt.secret}") String secret
    ) {
        this.issuer = issuer;
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** Throws if invalid/expired. Returns claims (Map) if valid. */
    public Map<String, Object> parseClaims(String jwt) {
        var jws = Jwts.parser()
                .requireIssuer(issuer)
                .verifyWith(key)
                .clockSkewSeconds(120) // ✅ correct for 0.12.x
                .build()
                .parseSignedClaims(jwt);

        return jws.getPayload(); // standard + custom claims
    }

    public String getSubject(String jwt) {
        return (String) parseClaims(jwt).get("sub");
    }
}