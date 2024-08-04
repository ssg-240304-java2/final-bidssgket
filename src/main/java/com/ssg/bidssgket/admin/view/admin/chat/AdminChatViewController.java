package com.ssg.bidssgket.admin.view.admin.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/chat")
public class AdminChatViewController {

    /**
     * 채팅 목록 조회 페이지
     */
    @GetMapping("/list")
    public String userListPage() {
        log.info("===== chat list page =====");

        return "admin/content/pages/chat/chat-list";
    }
}
