package com.anatonelly.freteexpress.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginMotoristaController {

    @GetMapping("/login")
    public ModelAndView showLogin() {
        return new ModelAndView("loginMotorista");
    }
}