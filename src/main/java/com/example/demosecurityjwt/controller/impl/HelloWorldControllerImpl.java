package com.example.demosecurityjwt.controller.impl;

import com.example.demosecurityjwt.controller.dto.ResponseDTO;
import com.example.demosecurityjwt.controller.interfaces.HelloWorldController;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HelloWorldControllerImpl implements HelloWorldController {

    @GetMapping("/hello/anon")
    public ResponseDTO helloAnon(Optional<String> name) {
        ResponseDTO responseDTO = new ResponseDTO();
        if(name.isPresent()) {
            responseDTO.setMessage("Hello, " + name.get());
        } else {
            responseDTO.setMessage("Hello Anon");
        }
        return responseDTO;
    }

    @GetMapping("/hello/user")
    public ResponseDTO helloUser() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Hello User");
        return responseDTO;
    }

    @GetMapping("/hello/admin")
    public ResponseDTO helloAdmin(Authentication authentication) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Hello Admin " + authentication.getName());
        return responseDTO;
    }

    @GetMapping("/curses/{xx}/department")
    public ResponseDTO helloX(@PathVariable String xx) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Hello " + xx);
        return responseDTO;
    }

    @GetMapping("/hello/david")
    public ResponseDTO helloDavid() {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Hello David");
        return responseDTO;
    }


}
