package com.journalapp.ellis.journalapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

    @RequestMapping({"/"})
    public String loadUI() {
        //log.info("loading UIssdd");
        return "forward:/index.html";
    }

}
