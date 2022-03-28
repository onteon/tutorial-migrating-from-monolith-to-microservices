package com.example.monolith.frontend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Patryk Borchowiec
 */
@Controller
public class FrontendController {
    @GetMapping
    private String landingPage() {
        return "index";
    }
}
