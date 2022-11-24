package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            String authority = grantedAuthority.getAuthority();
            rolesList.add(authority);
        }

        claims.put("roles", rolesList);

        Date issueDate = new Date();
        Date expireDate = new Date(issueDate.getTime()+jwtLifetime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issueDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Claims getAllClaimsFromToken(String toke){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(toke)
                .getBody();
    }

    public String getUsernameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public List<String> getRoles(String token){
        return getAllClaimsFromToken(token).get("roles", List.class);
    }
}
