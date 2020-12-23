package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.config.RabbitMQConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Wei
 * @date 2020/12/23 22:03
 */
@Service
@Slf4j
public class RabbitMQServiceImpl {

    @Autowired
    JavaMailSender mailSender;

    private static final String REGISTER_SUBJECT = "Welcome to use 2SeeOrNot";

    @RabbitListener(queues = RabbitMQConfig.REGISTER_QUEUE)
    public void sendEmail(Map<String, Object> map){
        log.info("ready to sent email to user {}", map);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2SeeOrNotOfficialwebsite@gmail.com");
        message.setTo((String) map.get("email"));
        message.setSubject(REGISTER_SUBJECT);
        message.setText(String.valueOf(map.get("verifycode")));
        mailSender.send(message);
        log.info("sended email to {}", map.get("email"));
    }
}
