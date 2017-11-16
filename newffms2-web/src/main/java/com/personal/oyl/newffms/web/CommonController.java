package com.personal.oyl.newffms.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {
    @RequestMapping("/login")
    public String login() {
        return "login";
    }
    
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
    
    @RequestMapping(value = "/accessDeniedRouter")
    public String errorRouter(@RequestParam("q") String resource) {
        return "redirect:/" + resource;
    }
    
    @RequestMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
