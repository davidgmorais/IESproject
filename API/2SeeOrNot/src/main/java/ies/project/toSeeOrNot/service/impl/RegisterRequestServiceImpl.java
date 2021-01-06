package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.PageDTO;
import ies.project.toSeeOrNot.entity.RegisterRequest;
import ies.project.toSeeOrNot.repository.RegisterRequestRepository;
import ies.project.toSeeOrNot.service.RegisterRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Wei
 * @date 2021/1/5 17:34
 */
@Service
public class RegisterRequestServiceImpl implements RegisterRequestService {

    @Autowired
    RegisterRequestRepository registerRequestRepository;

    @Override
    public boolean exists(String email) {
        return registerRequestRepository.existsByUserEmail(email);
    }

    @Override
    public void save(RegisterRequest registerRequest) {
        registerRequestRepository.save(registerRequest);
    }

    @Override
    @Cacheable(value = "request", key = "#root.methodName+'['+#page+']'", unless = "#result == null")
    public PageDTO<RegisterRequest> getRegisters(int page) {
        Page<RegisterRequest> precessed = registerRequestRepository.findAll(PageRequest.of(page, 10, Sort.by("precessed").ascending()));
        return new PageDTO<>(new HashSet<>(precessed.getContent()), precessed.getTotalPages(), precessed.getTotalElements());
    }

    @Override
    @Cacheable(value = "request", key = "#root.methodName", unless = "#result == null")
    public int getNumberOfRequestsNotProcessed() {
        return registerRequestRepository.getNumberOfRequestsNotProcessed();
    }

    @Override
    @Cacheable(value = "request", key = "#root.methodName+'['+#id+']'", unless = "#result == null")
    public RegisterRequest getRequestById(int id) {
        return registerRequestRepository.getRegisterRequestById(id);
    }

    @Override
    public boolean accept(int id) {
        if (registerRequestRepository.existsById(id))
            return registerRequestRepository.accept(id);
        return false;
    }

    @Override
    public boolean refuse(int id) {
        if (registerRequestRepository.existsById(id))
            return registerRequestRepository.refuse(id);
        return false;
    }
}
