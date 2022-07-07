package kg.peaksoft.bilingualb4.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void send(String to, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("peaksoft@gmail.com");
        simpleMailMessage.setSubject("test");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setText(message);
        this.javaMailSender.send(simpleMailMessage);
    }
}
