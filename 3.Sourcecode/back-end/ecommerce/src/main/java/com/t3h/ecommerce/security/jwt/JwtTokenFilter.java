package com.t3h.ecommerce.security.jwt;

import com.t3h.ecommerce.security.userprincal.UserDetailService;
import com.t3h.ecommerce.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class JwtTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JCacheCacheManager cacheManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (url.contains("/api/public") || url.contains("/api/auth") ||
                url.contains("swagger-resources") || url.contains("swagger-ui") ||
                url.contains("api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String token = jwtProvider.getJwt(request);
            String username = jwtProvider.getUserNameFromToken(token);
            String jwtToken = redisTemplate.opsForValue().get(username).toString();

            logger.error(jwtToken);
            
            if (token != null && jwtProvider.validateToken(token) && jwtToken != null) {
                List<String> roles = jwtProvider.getUserRolesFromToken(token);
                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities()));
                }
                filterChain.doFilter(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            }
        } catch (ExpiredJwtException e) {
            logger.error("Token has expired -> Message: {}", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
        } catch (Exception e) {
            logger.error("Can't set user authentication -> Message: {}", e);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        }
    }

}
