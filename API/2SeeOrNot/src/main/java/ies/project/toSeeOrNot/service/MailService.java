package ies.project.toSeeOrNot.service;

import java.util.Map;

/**
 * @author Wei
 * @date 2020/12/21 15:31
 */
public interface MailService {
    void sendEmail(Map<String, Object> map);
}
