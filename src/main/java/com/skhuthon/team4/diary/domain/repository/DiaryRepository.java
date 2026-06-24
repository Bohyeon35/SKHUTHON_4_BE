package com.skhuthon.team4.diary.domain.repository;

import com.skhuthon.team4.diary.domain.Diary;
import com.skhuthon.team4.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    // 하루 1개 제한 체크
    boolean existsByMemberAndDiaryDate(Member member, LocalDate diaryDate);

    // 최신순 피드
    List<Diary> findAllByIsPublicTrueOrderByCreatedAtDesc();

    // 랜덤 피드
    @Query(value = "SELECT * FROM diaries ORDER BY RAND()", nativeQuery = true)
    List<Diary> findAllRandom();

    // 핫 피드 top 10
    List<Diary> findTop10ByIsPublicTrueOrderByEmpathyCountDescCreatedAtDesc();

    // 내 일기 (년/월 필터)
    @Query("SELECT d FROM Diary d WHERE d.member = :member " +
            "AND YEAR(d.diaryDate) = :year AND MONTH(d.diaryDate) = :month " +
            "ORDER BY d.diaryDate DESC")
    List<Diary> findByMemberAndYearAndMonth(
            @Param("member") Member member,
            @Param("year") int year,
            @Param("month") int month
    );

    // 내가 쓴 일기 수
    int countByMember(Member member);

    // 내 일기들의 총 공감 수
    @Query("SELECT COALESCE(SUM(d.empathyCount), 0) FROM Diary d WHERE d.member = :member")
    int sumEmpathyCountByMember(@Param("member") Member member);

    // 내용 기준 공개 일기 검색
    @Query("SELECT d FROM Diary d WHERE d.isPublic = true AND d.content LIKE %:keyword% ORDER BY d.createdAt DESC")
    List<Diary> searchByContent(@Param("keyword") String keyword);

    // 나만 보기 (비공개 포함 내 일기)
    List<Diary> findByMemberAndDiaryDateBetweenOrderByCreatedAtDesc(
            Member member, LocalDate start, LocalDate end
    );

    // 나만 보기 비공개만
    List<Diary> findByMemberAndIsPublicFalseOrderByCreatedAtDesc(Member member);
}