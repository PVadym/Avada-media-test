package com.example.kino.endpoint.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
@RequiredArgsConstructor
public class FilmController {

    @GetMapping("/test")
    public ResponseEntity<String> createUser(){
        return ResponseEntity.ok("test");
    }
}
