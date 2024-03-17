package com.learn.CarBooking.service.impl;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.learn.CarBooking.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

  @Value("${spring.mail.username}")
  private String fromEmail;

  @Autowired
  private JavaMailSender javaMailSender;

  @Override
  public String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();

      MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

      mimeMessageHelper.setFrom(fromEmail);
      mimeMessageHelper.setTo(to);
      if (cc != null) {
        mimeMessageHelper.setCc(cc);
      }
      mimeMessageHelper.setSubject(subject);
      mimeMessageHelper.setText(body, true);

      if (file != null) {
        for (int i = 0; i < file.length; i++) {
          mimeMessageHelper.addAttachment(
              file[i].getOriginalFilename(),
              new ByteArrayResource(file[i].getBytes()));
        }
      }

      javaMailSender.send(mimeMessage);

      return "Mail sent";

    } catch (Exception e) {
      throw new RuntimeException(e);
    }

  }
}
