package com.karthic.ecommerce.service;

import com.karthic.ecommerce.payload.AuthenticationResult;
import com.karthic.ecommerce.payload.UserResponse;
import com.karthic.ecommerce.security.request.LoginRequest;
import com.karthic.ecommerce.security.request.SignupRequest;
import com.karthic.ecommerce.security.response.MessageResponse;
import com.karthic.ecommerce.security.response.UserInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {

    AuthenticationResult login(LoginRequest loginRequest);

    ResponseEntity<MessageResponse> register(SignupRequest signUpRequest);

    UserInfoResponse getCurrentUserDetails(Authentication authentication);

    ResponseCookie logoutUser();

    UserResponse getAllSellers(Pageable pageable);
}
