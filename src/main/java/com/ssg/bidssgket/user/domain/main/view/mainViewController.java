package com.ssg.bidssgket.user.domain.main.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class mainViewController {
    @GetMapping("/user/main/mainpage")
    public String mainpageController(){
        return "user/main/mainpage";
    }
}
