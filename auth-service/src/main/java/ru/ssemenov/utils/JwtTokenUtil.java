package ru.ssemenov.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.ssemenov.entities.Role;
import ru.ssemenov.entities.User;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.lifetime}")
    private Integer jwtLifetime;

    public String generateToken(User user){
        Map<String, Object> claims = new HashMap<>();
        List<String> rolesList = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        claims.put("roles", rolesList);
        claims.put("vatCode", user.getCompanyVAT());
        Date issueDate = new Date();
        Date expireDate = new Date(issueDate.getTime()+jwtLifetime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(issueDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}
