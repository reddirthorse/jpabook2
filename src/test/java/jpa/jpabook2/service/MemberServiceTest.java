package jpa.jpabook2.service;

import jpa.jpabook2.domain.Member;
import jpa.jpabook2.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Test
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");
        //when
        Long saveId = memberService.join(member);

        //then
        assertEquals(member,memberRepository.findOne(saveId));
    }
    @Test
    public void 중복확인() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kim1");
        Member member2 = new Member();
        member2.setName("kim1");
        //when
        memberService.join(member1);
        try {
            memberService.join(member2);//예외 발생
        }catch (IllegalStateException e){
            return;
        }


        //then
        fail("예외가 발생해야합니다.");
    }

}