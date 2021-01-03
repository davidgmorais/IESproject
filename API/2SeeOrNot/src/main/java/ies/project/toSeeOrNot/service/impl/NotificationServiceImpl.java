package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.entity.Notification;
import ies.project.toSeeOrNot.repository.NotificationRepository;
import ies.project.toSeeOrNot.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Wei
 * @date 2021/1/1 12:56
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Override
    public void createNotification(int senderId, int receiverId, String title, String msg, NoficationType type, int dataId) {
        Notification notification = new Notification();
        notification.setCreated(new Date());
        notification.setSender(senderId);
        notification.setReceiver(receiverId);
        notification.setRead(false);
        notification.setTitle(title);
        notification.setTitle(msg);
        notification.setType(type.getType());
        notification.setData(dataId);
        notificationRepository.save(notification);
    }
}
