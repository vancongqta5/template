package com.t3h.ecommerce.security.jwt;

import com.t3h.ecommerce.security.userprincal.UserPrinciple;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);


    @Value("${jwt.secret.key}")

    private String jwtSecret;

    @Value("${jwt.time.expire.token}")
    private int jwtExpriration ;

    @Value("${jwt.time.expire.refresh-token}")
    private int jwtExprirationRefresh ;

    public String createToken(Authentication authentication, Date now){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        List<String> roles = userPrinciple.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + jwtExpriration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String createRefreshToken(Authentication authentication, Date now){
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return Jwts.builder().setSubject(userPrinciple.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + jwtExprirationRefresh))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            logger.error("Invalid Jwt signture -> message: {}", e);
        }
        catch (ExpiredJwtException e){
            logger.error("expiredJwt -> message: {}", e);
        }
        catch (UnsupportedJwtException e){
            logger.error("unsupport Jwt  -> message: {}", e);
        }
        catch (MalformedJwtException e){
            logger.error("Malformed Jwt  -> message: {}", e);

        } catch (IllegalArgumentException e){
            logger.error("IllegalArgument Jwt  -> message: {}", e);
        }
        return false;
    }

    public String getUserNameFromToken(String token){
        String userName = Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token).getBody()
                .getSubject();
        return userName;
    }

    public List<String> getUserRolesFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        List<String> roles = (List<String>) claims.get("roles");
        return roles;
    }
    public String getJwt(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.replace("Bearer ","");
        }
        return null;
    }



}
