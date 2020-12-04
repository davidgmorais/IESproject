package ies.project.toSeeOrNot.service;
import ies.project.toSeeOrNot.entity.JwtUser;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.exception.UserNotFoundException;
import ies.project.toSeeOrNot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Wei
 * @date 2020/12/1 16:35
 */
@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /**
     * in our system, user's identifier is his email. Not username
     * @param email user's email
     * @return Spring security UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.getUserByUserEmail(email);
        if (user.isPresent())
            return new JwtUser(user.get());
        throw new UserNotFoundException();
    }

}
