package com.skhuthon.team4.notification;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // 일기 미작성 알림
    public void sendDiaryReminder(String toEmail, String nickname) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("[청춘잇다] 오늘 하루를 기록해보세요 ✏️");
            helper.setFrom("청춘잇다 <noreply@cheongchun.com>");

            String html = """
                <div style="font-family: 'Apple SD Gothic Neo', sans-serif; max-width: 480px; margin: 0 auto; padding: 32px 24px; background-color: #ffffff;">
                    <h2 style="color: #333333; font-size: 20px; margin-bottom: 8px;">안녕하세요, %s님! 👋</h2>
                    <p style="color: #555555; font-size: 15px; line-height: 1.8;">
                        오늘 하루를 아직 기록하지 않으셨네요.<br>
                        하루를 한 줄로 남겨보는 건 어떨까요?
                    </p>
                    <div style="margin: 32px 0; text-align: center;">
                        <a href="https://cheongchun-v1.vercel.app/write"
                           style="background-color: #4A90D9; color: #ffffff; padding: 14px 32px;
                                  border-radius: 8px; text-decoration: none; font-size: 15px; font-weight: bold;">
                            ✏️ 오늘 일기 쓰러 가기
                        </a>
                    </div>
                    <p style="color: #aaaaaa; font-size: 12px; text-align: center; margin-top: 32px;">
                        청춘잇다 팀 드림
                    </p>
                </div>
                """.formatted(nickname);

            helper.setText(html, true);
            mailSender.send(message);
            log.info("일기 알림 이메일 발송 성공: {}", toEmail);
        } catch (Exception e) {
            log.error("일기 알림 이메일 발송 실패: {} - {}", toEmail, e.getMessage());
        }
    }

    // AI 멘트 이메일
    public void sendAiComment(String toEmail, String nickname, String aiComment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("[청춘잇다] 오늘 하루를 응원합니다 💌");
            helper.setFrom("청춘잇다 <noreply@cheongchun.com>");

            String html = """
                <div style="font-family: 'Apple SD Gothic Neo', sans-serif; max-width: 480px; margin: 0 auto; padding: 32px 24px; background-color: #ffffff;">
                    <h2 style="color: #333333; font-size: 20px; margin-bottom: 8px;">%s님, 안녕하세요! 🌅</h2>
                    <p style="color: #555555; font-size: 15px; line-height: 1.8;">
                        오늘 작성하신 일기를 AI가 읽고 한 마디 전합니다.
                    </p>
                    <div style="background-color: #f5f8ff; border-left: 4px solid #4A90D9;
                                padding: 16px 20px; border-radius: 8px; margin: 24px 0;">
                        <p style="color: #333333; font-size: 15px; line-height: 1.8; margin: 0;">
                            💬 %s
                        </p>
                    </div>
                    <div style="margin: 32px 0; text-align: center;">
                        <a href="https://cheongchun-v1.vercel.app"
                           style="background-color: #4A90D9; color: #ffffff; padding: 14px 32px;
                                  border-radius: 8px; text-decoration: none; font-size: 15px; font-weight: bold;">
                            오늘도 일기 쓰러 가기
                        </a>
                    </div>
                    <p style="color: #aaaaaa; font-size: 12px; text-align: center; margin-top: 32px;">
                        청춘잇다 팀 드림
                    </p>
                </div>
                """.formatted(nickname, aiComment);

            helper.setText(html, true);
            mailSender.send(message);
            log.info("AI 멘트 이메일 발송 성공: {}", toEmail);
        } catch (Exception e) {
            log.error("AI 멘트 이메일 발송 실패: {} - {}", toEmail, e.getMessage());
        }
    }
}