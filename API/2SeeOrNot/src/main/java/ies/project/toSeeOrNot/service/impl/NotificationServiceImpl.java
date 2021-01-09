package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.dto.UserDTO;
import ies.project.toSeeOrNot.entity.Notification;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.repository.NotificationRepository;
import ies.project.toSeeOrNot.repository.UserRepository;
import ies.project.toSeeOrNot.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Wei
 * @date 2021/1/1 12:56
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserService userService;

    @Autowired
    PremierService premierService;

    @Autowired
    CinemaService cinemaService;

    @Autowired
    CommentService commentService;

    @Override
    public void createNotification(int senderId, int receiverId, String title, String msg, NoficationType type, int dataId) {
        Notification notification = new Notification();
        notification.setCreated(new Date());
        notification.setSender(senderId);
        notification.setReceiver(receiverId);
        notification.setRead(false);
        notification.setTitle(title);
        notification.setMessage(msg);
        notification.setType(type.getType());
        notification.setData(dataId);
        notificationRepository.save(notification);
    }

    @Override
    public Set<NotificationDTO> getNotificationsByUserId(int id, Pageable page) {
        List<Notification> allNotificationsOfCurrentUser = notificationRepository.findAllByReceiver(id, page).getContent();

        if (allNotificationsOfCurrentUser.size() == 0) {
            return new HashSet<>();
        }

        // receiver is always the same (current user)
        UserDTO receiver = userService.getUserById(allNotificationsOfCurrentUser.get(0).getReceiver());

        return allNotificationsOfCurrentUser.stream().map(notification -> {
                //create notificationDto
                NotificationDTO notificationDTO = new NotificationDTO();
                BeanUtils.copyProperties(notification, notificationDTO);

                //create userDto for receiver
                UserDTO receiverDTO = new UserDTO();
                BeanUtils.copyProperties(receiver, receiverDTO);
                notificationDTO.setReceiver(receiverDTO);

                //create userDto for sender
                UserDTO sender = userService.getUserById(notification.getSender());
                notificationDTO.setSender(sender);

                switch (notification.getType()){
                    case "premier":
                        notificationDTO.setData(premierService.getPremierById(notification.getData()));
                        break;

                    case "cinema":
                        notificationDTO.setData(cinemaService.getCinemaById(notification.getData()));
                        break;

                    case "comment":
                        notificationDTO.setData(cinemaService.getCinemaById(notification.getData()));
                        break;
                }

                return notificationDTO;
        }).collect(Collectors.toSet());
    }
    @Override
    public int getNumberOfNotificationsUnreadByUser(int user) {
        return notificationRepository.getNotificationsByReceiverAndReadFalse(user).size();
    }
}
