package com.example.posts.Security;

import com.example.posts.Entity.Role;
import com.example.posts.Service.Impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final UserServiceImpl userService;

    @Value("${security.provider.secret}")
    private String secret;

    @Value("${security.provider.validate}")
    private Long validateInMillisecond;


    @PostConstruct
    public void init(){
        secret = Base64.getEncoder().encodeToString(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Autowired
    public JwtTokenProvider(UserServiceImpl userService) {
        this.userService = userService;
    }

    public String createToken(String email, Role role){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", role);

        Date issue = new Date();
        Date ex = new Date(issue.getTime() + validateInMillisecond);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issue)
                .setExpiration(ex)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getEmail(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication authentication(String token){
        UserDetails userDetails = userService.loadUserByUsername(getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest httpServletRequest){
        String bearer = httpServletRequest.getHeader("Authorization");
        if(bearer != null && bearer.startsWith("Bearer ")){
            return bearer.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token){
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }
        catch (Exception e){
            return false;
        }
    }
}
