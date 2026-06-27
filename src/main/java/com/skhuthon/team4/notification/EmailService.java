package com.skhuthon.team4.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // 일기 미작성 알림
    public void sendDiaryReminder(String toEmail, String nickname) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("[청춘 한 줄 일기] 오늘 하루를 기록해보세요 ✏️");
            message.setText(
                    nickname + "님, 안녕하세요!\n\n" +
                            "오늘 하루를 아직 기록하지 않으셨네요.\n" +
                            "하루를 한 줄로 남겨보는 건 어떨까요?\n\n" +
                            "✏️ 지금 바로 일기 쓰러 가기\n" +
                            "https://cheongchun-v1.vercel.app/write\n\n" +
                            "- 청춘 한 줄 일기 팀 드림"
            );
            mailSender.send(message);
            log.info("일기 알림 이메일 발송 성공: {}", toEmail);
        } catch (Exception e) {
            log.error("일기 알림 이메일 발송 실패: {} - {}", toEmail, e.getMessage());
        }
    }

    // AI 멘트 이메일
    public void sendAiComment(String toEmail, String nickname, String aiComment) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("[청춘 한 줄 일기] 어제 하루를 돌아보며 💌");
            message.setText(
                    nickname + "님, 좋은 아침이에요! 🌅\n\n" +
                            "어제 작성하신 일기를 AI가 읽고 한 마디 전합니다.\n\n" +
                            "💬 " + aiComment + "\n\n" +
                            "오늘도 청춘잇다와 함께 하루를 기록해보세요!\n" +
                            "https://cheongchun-v1.vercel.app\n\n" +
                            "- 청춘 한 줄 일기 팀 드림"
            );
            mailSender.send(message);
            log.info("AI 멘트 이메일 발송 성공: {}", toEmail);
        } catch (Exception e) {
            log.error("AI 멘트 이메일 발송 실패: {} - {}", toEmail, e.getMessage());
        }
    }
}