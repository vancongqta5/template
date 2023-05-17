package com.t3h.ecommerce.service.Impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.t3h.ecommerce.dto.request.SignInForm;
import com.t3h.ecommerce.dto.request.SignUpForm;
import com.t3h.ecommerce.dto.response.JwtResponse;
import com.t3h.ecommerce.dto.response.ResponseMessage;
import com.t3h.ecommerce.entities.core.Role;
import com.t3h.ecommerce.entities.core.RoleName;
import com.t3h.ecommerce.entities.core.User;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.security.jwt.JwtProvider;
import com.t3h.ecommerce.security.userprincal.UserPrinciple;
import com.t3h.ecommerce.service.AuthService;
import com.t3h.ecommerce.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    JCacheCacheManager cacheManager;

    @Autowired
    ObjectMapper  objectMapper;



    @Autowired
    RedisTemplate redisTemplate;


    @Override
    @Transactional
    public ResponseEntity<?> signUp(SignUpForm signUpForm) {
        if(userService.existsByUsername(signUpForm.getUserName())){
            return new ResponseEntity<>(new ResponseMessage("nouser"), HttpStatus.OK);
        }
        else if(userService.existsByEmail(signUpForm.getEmail())){
            return new ResponseEntity<>(new ResponseMessage("noemail"), HttpStatus.OK);
        }

        Date now = new Date();
        String passwordEncode = passwordEncoder.encode(signUpForm.getPassword());
        User user = new User(
                now.getTime(), now.getTime(),
                signUpForm.getUserName(),
                passwordEncode,
                signUpForm.getEmail(),
                signUpForm.getFullName(),
                signUpForm.getAvatar(),
                signUpForm.getGender(),
                signUpForm.getPhoneNumber(),
                signUpForm.getAddress()
        );
        List<Role> roles = new ArrayList<>();
        Role userRole = roleService.findByRoleName(RoleName.USER).orElseThrow(() -> new RuntimeException("Role not found"));
        roles.add(userRole);
        user.setRoles(roles);
        user.setStatus(1);
        userService.save(user);

        return new ResponseEntity<>(new ResponseMessage("success"), HttpStatus.OK);
    }


    Date now = new Date();
    @Override
    public ResponseEntity<?> signIn(SignInForm signInForm) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            signInForm.getUsername(), signInForm.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.createToken(authentication, now);
            String refreshToken = jwtProvider.createRefreshToken(authentication, now);
            User user = userService.findByUsername(signInForm.getUsername()).orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
            if(user.getStatus() == 0){
                return new ResponseEntity<>(new ResponseMessage("locked"), HttpStatus.OK);
            }
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

            redisTemplate.opsForValue().set(signInForm.getUsername(), token);
            return new ResponseEntity<>(new JwtResponse(token,
                    refreshToken,
                    userPrinciple.getId(),
                    userPrinciple.getFullName(),
                    userPrinciple.getAuthorities(),
                    userPrinciple.getAvatar()), HttpStatus.OK);

        }catch (Exception exception ){
            return new ResponseEntity<>(new ResponseMessage("fail"),  HttpStatus.OK);
        }
    }

    private void putTokenToCache(String token, User user) {
        Cache cacheJwt = cacheManager.getCache(Constants.CACHE_JWT);

        if(cacheJwt == null){
            log.error("get cache error");
        }
        else{
            cacheJwt.put(user.getUsername(), token);
        }
    }

    public BaseResponse refreshToken(HttpServletRequest request){

        Cache cache_jwt = cacheManager.getCache(Constants.CACHE_JWT);
        if(cache_jwt == null){
            return BaseResponse.builder().message("get cache error").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        String refreshToken = request.getHeader(Constants.REFRESH_TOKEN);
        if(refreshToken == null || !jwtProvider.validateToken(refreshToken)){
            return BaseResponse.builder().message("refresh token invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        String username = jwtProvider.getUserNameFromToken(refreshToken);
        User user = userService.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("username not found!"));
        String jwtOld = cache_jwt.get(username, String.class);
        if(jwtOld == null || !jwtOld.isEmpty()){
            return BaseResponse.builder().message("user logged out").status(HttpStatus.BAD_REQUEST.value()).build();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );
        ObjectNode data = objectMapper.createObjectNode();
            data.put("token", jwtProvider.createToken(authentication, now));
            data.put("refreshToken", jwtProvider.createRefreshToken(authentication, now));
        return BaseResponse.builder().message("refresh token success").data(data).status(HttpStatus.OK.value()).build();
    }

    public BaseResponse logOut(HttpServletRequest request){
        String token_jwt = jwtProvider.getJwt(request);
        if(!jwtProvider.validateToken(token_jwt)){
            return  BaseResponse.builder().message("token invalid").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        String username = jwtProvider.getUserNameFromToken(token_jwt);
        String cacheJwt = redisTemplate.opsForValue().get(username).toString();
        if(cacheJwt == null){
            log.error("get cache error");
            return  BaseResponse.builder().message("get cache error").status(HttpStatus.BAD_REQUEST.value()).build();
        }
        else {
            redisTemplate.opsForValue().getOperations().delete(username);
            return BaseResponse.builder().message("logout success").status(HttpStatus.OK.value()).build();
        }
//        return BaseResponse.builder().message("logout success").status(HttpStatus.OK.value()).build();

    }
}
