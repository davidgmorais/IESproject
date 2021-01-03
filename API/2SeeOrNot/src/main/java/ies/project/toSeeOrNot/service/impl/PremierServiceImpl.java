package ies.project.toSeeOrNot.service.impl;

import ies.project.toSeeOrNot.dto.PremierDTO;
import ies.project.toSeeOrNot.entity.Premier;
import ies.project.toSeeOrNot.repository.PremierRepository;
import ies.project.toSeeOrNot.service.PremierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wei
 * @date 2020/12/31 20:34
 */
@Service
public class PremierServiceImpl implements PremierService {

    @Autowired
    PremierRepository premierRepository;

    @Override
    public PremierDTO getPremierById(int id) {
        Premier premierById = premierRepository.getPremierById(id);

        return null;
    }
}
