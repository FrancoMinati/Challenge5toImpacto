package com.example.challengequintoimpacto.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping("/")
    public String index(ModelMap model){
        return "inicio";
    }
    @GetMapping("/inicio")
    public String inicio(ModelMap model){
        return "inicio";
    }


}
