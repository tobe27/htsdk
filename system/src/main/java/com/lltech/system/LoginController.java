package com.lltech.system;

import com.lltech.common.utils.JwtUtils;
import com.lltech.common.utils.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * @author CREATED BY L.C.Y on 2019-3-29
 */

@RestController
@RequestMapping("/")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/login")
    public ResultBean login() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", "li");
        String token = JwtUtils.generateToken(payload);
        log.info("username: li" + ",token: " + token);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(token, token);
        subject.login(usernamePasswordToken);
        return new ResultBean().ok();
    }
}
