package com.example.asset_management_idnes.service;

import com.example.asset_management_idnes.domain.Otp;
import com.example.asset_management_idnes.domain.User;
import com.example.asset_management_idnes.repository.OtpRepository;
import com.example.asset_management_idnes.repository.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)

public class EmailService {


    final UserRepository userRepository;

    final OtpRepository otpRepository;

    @Autowired
    private  JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private  String sender;


    Random rd = new Random();

    public EmailService(UserRepository userRepository, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.otpRepository = otpRepository;
    }


    @Async
    public void sendActivationEmail(String email) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(email);
            helper.setSubject("[IDNES-ASSET-MANAGEMENT] Kích Hoạt Tài Khoản");

            String activationLink = "http://localhost:8080/api/v1/users/active-account/" + email;
            String emailContent = "Bạn đã được tạo tài khoản trên ứng dụng Quản lý tài sản IDNES, ấn <a href=\"" + activationLink + "\">đây</a> để kích hoạt tài khoản của bạn.";
            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("Error while sending mail!!!");
        }
    }



    @Async
    public void sendEmailToRecipients(String subject, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(sender);
        helper.setTo("quanghien485@gmail.com");
        helper.addTo("kimlamvu1z@gmail.com");
        helper.setSubject(subject);
        helper.setText(text);
        javaMailSender.send(message);
    }

    @Async
    public void sendOtpActivatedAccount(String email) {
        String otpCode = UUID.randomUUID().toString();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(email);

            mimeMessageHelper.setSubject("[IDNES] Kích Hoạt Tài Khoản");
            String htmlContent = "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "\n" +
                    "<head>\n" +
                    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                    "  <meta name=\"x-apple-disable-message-reformatting\" />\n" +
                    "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                    "  <meta name=\"color-scheme\" content=\"light dark\" />\n" +
                    "  <meta name=\"supported-color-schemes\" content=\"light dark\" />\n" +
                    "  <title></title>\n" +
                    "  \n" +
                    "</head>\n" +
                    "\n" +
                    "<body>\n" +
                    "  <table class=\"email-wrapper\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                    "    <tr>\n" +
                    "      <td align=\"center\">\n" +
                    "        <table class=\"email-content\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\">\n" +
                    "          <tr>\n" +
                    "            <td class=\"email-masthead\">\n" +
                    "              <p>Bạn đã được tạo tài khoản trên ứng dụng Quản lý tài sản IDNES.</Strong></p>\n" +
                    "              <a href=\"http://localhost:8080/api/v1/users/active-account?otpCode=" + otpCode + "\" target=\"_blank\" >\n" +
                    "                Click vào đây để kích hoạt tài khoản của bạn.\n" +
                    "              </a>\n" +
                    "\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "          \n" +
                    "          <tr>\n" +
                    "            <td>\n" +
                    "              <table class=\"email-footer\" align=\"center\" width=\"570\" cellpadding=\"0\" cellspacing=\"0\"\n" +
                    "                role=\"presentation\">\n" +
                    "                <tr>\n" +
                    "                  <td class=\"content-cell\" align=\"center\">\n" +
                    "                    <p class=\"f-fallback sub align-center\">\n" +
                    "                      [Công ty TNHH Đầu tư và Phát triển Hệ thống đấu thầu qua mạng quốc gia]\n" +
                    "                      <br>519 Kim Mã, Ba Đình, Hà Nội.\n" +
                    "                      <br>\n" +
                    "                    </p>\n" +
                    "                  </td>\n" +
                    "                </tr>\n" +
                    "              </table>\n" +
                    "            </td>\n" +
                    "          </tr>\n" +
                    "        </table>\n" +
                    "      </td>\n" +
                    "    </tr>\n" +
                    "  </table>\n" +
                    "</body>\n" +
                    "\n" +
                    "</html>";
            mimeMessageHelper.setText(htmlContent, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println("Error while sending mail!!!");
        }

        Optional<User> emailOptional = userRepository.findByEmail(email);

        otpRepository.save(Otp.builder()
                .otpCode(otpCode)
                .user(emailOptional.get())
                .build());
    }
}
