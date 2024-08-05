package com.ssg.bidssgket.user.domain.main.view;

import org.springframework.web.bind.annotation.GetMapping;

public class mainViewController {
    @GetMapping("")
    public String mainpageController(){
        return "/user/auction/mainpage";
    }
}
