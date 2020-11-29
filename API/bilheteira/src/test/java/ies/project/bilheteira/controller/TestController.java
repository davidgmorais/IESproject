package ies.project.bilheteira.controller;

import ies.project.bilheteira.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wei
 * @date 2020/11/29 11:08
 */
@RestController
public class TestController {
    @GetMapping("/")
    public Result testMapping(){
        return Result.sucess("sucess");
    }
}
