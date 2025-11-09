package com.upt.app.auth.dto;

import com.upt.app.models.User;

public record TokenResponse(String accessToken, String refreshToken, User user) {}
