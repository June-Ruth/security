package com.training.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    @GetMapping("/home")
    public String home() {
        return "Home of the application";
    }

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

    @GetMapping("/")
    public String getGitHub(Principal user) {
        return "Welcome " + user.getName();
    }

}
