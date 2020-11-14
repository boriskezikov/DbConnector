package com.example.application.backend.controller;

import com.example.application.backend.service.BrowsingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("api/connection")
@RequiredArgsConstructor
public class ConnectionsController {

    private final BrowsingService browsingService;

    @PostMapping("/test")
    public void test(@RequestParam String q, @RequestParam BigInteger detailsId) {
        browsingService.executeUpdate(q, detailsId);
    }
}
