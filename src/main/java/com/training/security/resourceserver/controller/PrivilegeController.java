package com.training.security.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivilegeController {

    @GetMapping("/read")
    public String getRead() {
        return "Access to read message";
    }

    @GetMapping("/write")
    public String getWrite() {
        return "Access to write message";
    }

    @GetMapping("/delete")
    public String getDelete() {
        return "Access to delete message";
    }

}
