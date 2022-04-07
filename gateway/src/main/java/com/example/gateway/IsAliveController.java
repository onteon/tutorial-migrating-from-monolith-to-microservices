package com.example.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Patryk Borchowiec
 */
@RestController
public class IsAliveController {
    @GetMapping("/is-alive")
    public void isAlive() {
    }
}
