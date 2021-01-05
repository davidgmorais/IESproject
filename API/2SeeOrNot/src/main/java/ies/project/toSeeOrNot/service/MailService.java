package ies.project.toSeeOrNot.service;

import java.util.Map;

/**
 * @author Wei
 * @date 2020/12/21 15:31
 */
public interface MailService {
    void sendRegisterEmail(Map<String, Object> map);

    void sendPaymentEmail(Map<String, Object> map);

    void sendRequestReulstEmail(Map<String, Object> map);
}
