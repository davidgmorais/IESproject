package ies.project.toSeeOrNot.controller;
import ies.project.toSeeOrNot.common.Result;
import ies.project.toSeeOrNot.common.enums.Role;
import ies.project.toSeeOrNot.entity.User;
import ies.project.toSeeOrNot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wei
 * @date 2020/12/3 10:31
 */
@RestController
public class test {

    @GetMapping("/cinema")
    public Result cinemaRoleTest(){
        return Result.sucess("cinema");
    }

    @GetMapping("/admin")
    public Result adminRoleTest(){
        return Result.sucess("admin");
    }

    @GetMapping("/user")
    public Result userRoleTest(){
        return Result.sucess("user");
    }

    @GetMapping("/normal")
    public Result normal(){
        return Result.sucess("yes");
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public Result registerUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER.getRoleCode());
        User usersaved = userRepository.save(user);
        return Result.sucess(usersaved);
    }
}
