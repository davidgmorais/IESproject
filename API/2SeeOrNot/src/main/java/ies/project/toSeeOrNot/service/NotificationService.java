package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.dto.NotificationDTO;
import ies.project.toSeeOrNot.entity.Notification;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/1 12:54
 */
public interface NotificationService {
    void createNotification(int senderId,
                            int receiverId,
                            String title,
                            String msg,
                            NoficationType type,
                            int dataId);

    Set<NotificationDTO> getNotificationsByUserId(int id, Pageable pageable);

    int getNumberOfNotificationsUnreadByUser(int user);
}
