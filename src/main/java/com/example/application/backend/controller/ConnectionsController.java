package com.example.application.backend.controller;

import com.example.application.backend.service.ConnectionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@RestController
@RequestMapping("api/connection")
public class ConnectionsController {

    @Autowired
    private ConnectionsService connectionsService;


    @PostMapping("/destroy")
    public void destroy(HttpSession httpSession) {
        httpSession.invalidate();
    }

    @PostMapping("/test")
    public void test(@RequestParam String q, @RequestParam String instance) {
        connectionsService.execute(q,instance);
    }
}
