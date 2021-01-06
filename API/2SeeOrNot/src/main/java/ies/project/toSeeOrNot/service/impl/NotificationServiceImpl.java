package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.dto.UserDTO;
import ies.project.toSeeOrNot.entity.Notification;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.repository.NotificationRepository;
import ies.project.toSeeOrNot.repository.UserRepository;
import ies.project.toSeeOrNot.service.NotificationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/1 12:56
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserRepository userRepository;

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

    @Override
    @Cacheable(value = "notification", key = "#root.methodName+'['+#id+'_'+#page+']'", unless = "#result == null")
    public Set<NotificationDTO> getNotificationsByUserId(int id, Pageable page) {
        List<Notification> allNotificationsOfCurrentUser = notificationRepository.findAllByReceiver(id, page).getContent();

        if (allNotificationsOfCurrentUser.size() == 0){
            return new HashSet<>();
        }

        Set<NotificationDTO> notificationDTOS = new HashSet<>();

        User currentUser =  userRepository.findUserById(allNotificationsOfCurrentUser.get(0).getReceiver());

        allNotificationsOfCurrentUser.forEach(
                notification -> {
                    //create notificationDto
                    NotificationDTO notificationDTO = new NotificationDTO();
                    BeanUtils.copyProperties(notification, notificationDTO);

                    //create userDto for receiver
                    UserDTO receiver = new UserDTO();
                    BeanUtils.copyProperties(currentUser, receiver);
                    receiver.setRole(null);
                    notificationDTO.setReceiver(receiver);

                    //create userDto for sender
                    UserDTO sender = new UserDTO();
                    BeanUtils.copyProperties(currentUser, sender);
                    sender.setRole(null);
                    notificationDTO.setSender(sender);

                    notificationDTOS.add(notificationDTO);
                }
        );
        return notificationDTOS;
    }

    @Override
    @Cacheable(value = "notification", key = "#root.methodName+'['+#user+']'", unless = "#result == null")
    public int getNumberOfNotificationsUnreadByUser(int user) {
        return notificationRepository.getNumberOfUnreadNotificationsByUser(user);
    }
}
