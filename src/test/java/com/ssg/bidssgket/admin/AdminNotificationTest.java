package com.ssg.bidssgket.admin;

import com.ssg.bidssgket.admin.api.member.repository.AdminMemberApiRepository;
import com.ssg.bidssgket.admin.api.product.auction.service.AdminAuctionService;
import com.ssg.bidssgket.user.domain.member.domain.Member;
import groovy.util.logging.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Slf4j
@SpringBootTest
@Transactional
public class AdminNotificationTest {

    @Autowired
    AdminAuctionService adminAuctionService;

    @Autowired
    AdminMemberApiRepository adminMemberApiRepository;

    @Test
    public void findByBidSuccessIsTrueAndProduct(){
        // given
        Long productNo = 1L;


        // when
        List<Long> longs = adminAuctionService.sendAuctionClosed(productNo);

        // then
        assertThat(longs).isEqualTo(2);

    }

    @Test
    public void findMemberByIdTest(){
        // given
        Long memberNo = 1L;

        // when
        Member member = adminMemberApiRepository.findById(memberNo).get();

        // then
        assertThat(member.getMemberNo()).isEqualTo(memberNo);

    }
}
