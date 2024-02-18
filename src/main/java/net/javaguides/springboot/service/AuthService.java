package net.javaguides.springboot.service;

import net.javaguides.springboot.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
