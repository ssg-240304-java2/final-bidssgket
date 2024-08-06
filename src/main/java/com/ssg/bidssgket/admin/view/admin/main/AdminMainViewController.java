package com.ssg.bidssgket.admin.view.admin.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminMainViewController {


    /**
     * 메인 페이지
     */
//    @GetMapping("")
    public String mainPage() {

        return "admin/content/main";
    }






}
