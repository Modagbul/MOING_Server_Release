package com.moing.backend.domain.auth.application.service;

import java.io.IOException;

public interface WithdrawProvider {
    void withdraw(String token) throws IOException;
}
