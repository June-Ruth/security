package com.training.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/admin")
    public String welcomeAdmin() {
        return "Welcome Admin";
    }

    @GetMapping("/editor")
    public String welcomeEditor() {
        return "Welcome Editor";
    }

    @GetMapping("/user")
    public String welcomeUser() {
        return "Welcome User";
    }

    @GetMapping("/default")
    public String welcomeByDefault() {
        return "Welcome by default";
    }

}
