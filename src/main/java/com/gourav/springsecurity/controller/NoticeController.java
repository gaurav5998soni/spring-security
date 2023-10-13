package com.gourav.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticeController {

    @GetMapping("/notice")
    public String getNotice() {
        return "This is notice page";
    }
}
