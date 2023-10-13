package com.gourav.springsecurity.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("myProfile")
    public String getProfile() {
        return "This is profile page";
    }
}
