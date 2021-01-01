package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.common.enums.NoficationType;
import ies.project.toSeeOrNot.entity.Notification;

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
}
