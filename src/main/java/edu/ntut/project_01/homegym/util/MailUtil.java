package edu.ntut.project_01.homegym.util;

import edu.ntut.project_01.homegym.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;


@Slf4j
@Component
public class MailUtil {

    private String verifiedPath;
    @Value("${hg.url}")
    private String ourUrl;
    @Value("${spring.mail.username}")
    private String ourEmail;

    private final MemberRepository memberRepository;
    private final JavaMailSender mailSender;

    @Autowired
    public MailUtil(MemberRepository memberRepository, JavaMailSender mailSender) {
        this.memberRepository = memberRepository;
        this.mailSender = mailSender;
    }

    public void sendMail(String code, String memberEmail) throws MessagingException {

        verifiedPath = ourUrl + "/memberVerification?code=" + code;
//        verifiedPath = "http://localhost:8081/memberVerification?code=" + code;
        String name = memberRepository.findMemberByEmail(memberEmail).orElseThrow().getName();
        String subject = "主旨： " + name + " HomeGym會員驗證信";
        String html = "<html><body><div><div><img src=\"cid:logoPic\"/></div>" +
                "<div><div>請點選下方連結，完成認證程序。</div><a href=" + verifiedPath +
                ">請點擊此處來驗證您的帳號</a><br/><div>提醒您：本驗證信連結1小時內有效，若您於收到本驗證信後1小時內沒有啟動，<br/>" +
                "則需在帳戶資料中重寄電子信箱驗證信。<br/>有任何問題，歡迎來電洽詢HomeGym團隊客服專線0911-222-333。" +
                "<br/></div><br/><div>厝動HomeGym服務團隊 敬上</div></div></div></body></html>";

        createMail(memberEmail, subject, html);

        log.info("已寄信");
    }

    public void sendResetPassword(String memberEmail) throws MessagingException {

        verifiedPath = ourUrl+"/forgetPasswordInput";
//        verifiedPath = "http://localhost:8081/forgetPasswordInput";
        String name = memberRepository.findMemberByEmail(memberEmail).orElseThrow().getName();
        String subject = "主旨： " + name + " HomeGym密碼重置";
        String html = "<html><body><div><div><img src=\"cid:logoPic\"/></div><div><div>" +
                "請點擊下方，重新設置帳戶密碼。</div><button><a href=\"" + verifiedPath + "\"/>重設密碼</button><br/>" +
                "<div>提醒您：重設密碼後，請妥善保管並避免再次忘記。<br/>有任何問題，歡迎來電洽詢HomeGym團隊客服專線0911-222-333。<br/></div>" +
                "<br/><div>厝動HomeGym服務團隊 敬上</div></div></div></body></html>";

        createMail(memberEmail, subject, html);

        log.info("已寄信");
    }

    void createMail(String memberEmail, String subject, String html) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(ourEmail);
        helper.setTo(memberEmail);
        helper.setSubject(subject);

        helper.setText(html, true);

        try {
            helper.addInline("logoPic", new ClassPathResource("static/image/hg_logo/logoMail.png"));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("圖片路徑又出問題拉！！！！！！！！！！！");
            log.error(e.getMessage());
        }

        mailSender.send(mimeMessage);
    }

}
