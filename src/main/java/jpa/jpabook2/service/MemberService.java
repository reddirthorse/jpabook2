package jpa.jpabook2.service;

import jpa.jpabook2.domain.Member;
import jpa.jpabook2.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //exception
        List<Member>findMembers = memberRepository.findByMember(member.getName());
        if(!findMembers.isEmpty())
        {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }
    //회원 전체조회

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }
    //회원 한명 조회

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
