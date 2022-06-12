package com.project.all.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName:homeController
 * @Author:lxx
 * @Date 2022/5/3 16:59
 **/
@Controller
public class homeController {
    @GetMapping("/myLogin")
    public String login() {
        return "/login";
    }

    @GetMapping("/registry")
    public String register() {
        return "registry";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/authentic")
    public String authentic() {
        return "authentic";
    }

    @GetMapping("/user/add")
    public String add() {
        return "user/add";
    }

    @GetMapping("/user/update")
    public String update() {
        return "user/update";
    }

    @GetMapping("/user/sender")
    public String MailSender() {
        return "user/sender";
    }
    @GetMapping("/chat")
    public String chat(){
        return "chat";
    }
}
