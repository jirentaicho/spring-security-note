package com.session.id.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class XSSController {

    @GetMapping("/newpost")
    public String newPost(){
        return "create";
    }

    @PostMapping("/newpost")
    public String checkPost(@RequestParam(name="input") String input, Model model){
        model.addAttribute("input",input);
        return "check";
    }

}
