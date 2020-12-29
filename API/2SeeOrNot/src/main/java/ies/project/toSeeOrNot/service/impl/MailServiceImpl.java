package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Wei
 * @date 2020/12/21 15:32
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void senEmail(String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("weiye@ua.pt");
        simpleMailMessage.setSubject("Test from sprint boot");
        simpleMailMessage.setText(content);
        javaMailSender.send(simpleMailMessage);
    }
}
