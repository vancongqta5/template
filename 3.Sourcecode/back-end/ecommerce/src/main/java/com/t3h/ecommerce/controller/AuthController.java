package com.t3h.ecommerce.controller;


import com.t3h.ecommerce.dto.request.SignInForm;
import com.t3h.ecommerce.dto.request.SignUpForm;
import com.t3h.ecommerce.dto.response.BaseResponse;
import com.t3h.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

   @Autowired
   private final AuthService service;

   @PostMapping("/signup")
   public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm){
      return service.signUp(signUpForm);
   }

   @PostMapping("/login")
   public ResponseEntity<?> signIn(@Valid @RequestBody SignInForm signInForm){
      return service.signIn(signInForm);
   }

   @GetMapping("/logout")
   public BaseResponse logout(HttpServletRequest request){
      return service.logOut(request);
   }

   @GetMapping("/refresh-token")
   public BaseResponse refreshToken(HttpServletRequest request){
      return service.refreshToken(request);
   }
}
