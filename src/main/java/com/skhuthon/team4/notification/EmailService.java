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
                            "http://localhost:3000/write\n\n" +
                            "- 청춘 한 줄 일기 팀 드림"
            );
            mailSender.send(message);
            log.info("이메일 발송 성공: {}", toEmail);
        } catch (Exception e) {
            log.error("이메일 발송 실패: {} - {}", toEmail, e.getMessage());
        }
    }
}