package com.t3h.ecommerce.service;

import com.t3h.ecommerce.dto.request.SignInForm;
import com.t3h.ecommerce.dto.request.SignUpForm;
import com.t3h.ecommerce.dto.response.BaseResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {
    ResponseEntity<?> signUp(SignUpForm signUpForm);
    ResponseEntity<?> signIn(SignInForm signInForm);

    BaseResponse logOut(HttpServletRequest request);

    BaseResponse refreshToken(HttpServletRequest request);
}
