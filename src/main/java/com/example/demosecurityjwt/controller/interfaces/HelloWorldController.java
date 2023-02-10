package com.example.demosecurityjwt.controller.interfaces;

import com.example.demosecurityjwt.controller.dto.ResponseDTO;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface HelloWorldController {
    ResponseDTO helloAnon(Optional<String> name);
    ResponseDTO helloUser();
    ResponseDTO helloAdmin(Authentication authentication);
}
