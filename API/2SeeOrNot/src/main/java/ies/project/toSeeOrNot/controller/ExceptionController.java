package ies.project.toSeeOrNot.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Wei
 * @date 2020/12/20 18:39
 */
@RestController
public class ExceptionController {

    @PostMapping("/error/throw")
    public void throwException(HttpServletRequest request){
        throw (RuntimeException) request.getAttribute("Exception");
    }

}
