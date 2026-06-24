package com.skhuthon.team4.member.domain.repository;

import com.skhuthon.team4.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);
    // 알림 설정 ON인 유저 전체 조회
    List<Member> findAllByIsNotificationTrue();
}