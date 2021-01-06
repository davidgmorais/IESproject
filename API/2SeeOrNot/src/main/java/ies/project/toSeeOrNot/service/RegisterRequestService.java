package ies.project.toSeeOrNot.service;

import ies.project.toSeeOrNot.entity.RegisterRequest;

import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/5 17:33
 */
public interface RegisterRequestService {
    boolean exists(String email);

    void save(RegisterRequest registerRequest);

    Set<RegisterRequest> getRegisters(int page);

    int getNumberOfRequestsNotProcessed();

    RegisterRequest getRequestById(int id);

    boolean accept(int id);

    boolean refuse(int id);
}
