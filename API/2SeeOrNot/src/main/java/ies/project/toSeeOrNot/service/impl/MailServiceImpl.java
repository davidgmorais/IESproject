package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.config.RabbitMQConfig;
import ies.project.toSeeOrNot.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Wei
 * @date 2020/12/21 15:32
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;

    private static final String REGISTER_SUBJECT = "Welcome to use 2SeeOrNot";
    private static final String PAYMENT_SUBJECT = "Payment";
    private static final String Request_SUBJECT = "Request";


    @RabbitListener(queues = RabbitMQConfig.REGISTER_QUEUE)
    public void sendRegisterEmail(Map<String, Object> map){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2SeeOrNotOfficialwebsite@gmail.com");
        message.setTo((String) map.get("email"));
        message.setSubject(REGISTER_SUBJECT);
        message.setText(String.valueOf(map.get("verifycode")));
        mailSender.send(message);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.PAYMENT_QUEUE)
    public void sendPaymentEmail(Map<String, Object> map) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2SeeOrNotOfficialwebsite@gmail.com");
        message.setTo((String) map.get("email"));
        message.setSubject(PAYMENT_SUBJECT);
        message.setText((String) map.get("payment"));
        mailSender.send(message);
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.REQUEST_QUEUE)
    public void sendRequestReulstEmail(Map<String, Object> map) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2SeeOrNotOfficialwebsite@gmail.com");
        message.setTo((String) map.get("email"));
        message.setSubject(Request_SUBJECT);
        message.setText((String) map.get("request"));
        mailSender.send(message);
    }
}
