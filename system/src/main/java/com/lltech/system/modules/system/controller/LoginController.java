package com.lltech.system.modules.system.controller;

import com.lltech.common.utils.JwtUtils;
import com.lltech.common.utils.Md5Util;
import com.lltech.common.utils.ResultBean;
import com.lltech.common.utils.StringUtils;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.modules.system.dto.LoginUserDTO;
import com.lltech.system.modules.system.model.SysUserDO;
import com.lltech.system.modules.system.service.SysUserDOService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author CREATED BY L.C.Y on 2019-3-29
 */
@Slf4j
@RestController
@RequestMapping("/system")
public class LoginController {
    private final SysUserDOService sysUserDOService;

    @Autowired
    public LoginController(SysUserDOService sysUserDOService) {
        this.sysUserDOService = sysUserDOService;
    }

    @Log("用户登录")
    @PostMapping("/login")
    public ResultBean login(@RequestBody LoginUserDTO userDTO) {
        // 用户名不能为空
        if (userDTO == null || StringUtils.isBlank(userDTO.getUsername()))  {
            return new ResultBean().error("请输入用户名！");
        }

        // 密码不能为空
        if (StringUtils.isBlank(userDTO.getPassword())) {
            return new ResultBean().error("请输入密码！");
        }

        // 查询用户是否存在
        SysUserDO user = sysUserDOService.getUserByUsername(userDTO.getUsername());
        if (user == null) {
            return new ResultBean().error("用户名或密码不正确！");
        }
        // 判断用户名大小写是否相同
        if (!userDTO.getUsername().equals(user.getUsername())) {
            return new ResultBean().error("用户名或密码不正确！");
        }

        // 判断密码是否正确
        if (!Md5Util.validateMD5(user.getPassword(), userDTO.getPassword())) {
            return new ResultBean().error("用户名或密码不正确！");
        }

        // 是否被冻结 1-正常， 0-冻结
        if (user.getStatus() != 1) {
            return new ResultBean().error("用户已被冻结！");
        }

        // token中存放用户ID和用户名
        Map<String, Object> payload = new HashMap<>();
        payload.put("username", user.getUsername());
        payload.put("userId", user.getUserId());
        payload.put("status", user.getStatus());
        String token = JwtUtils.generateTokenOffExp(payload);
        log.info("{username=" + user.getUsername() + ", token=" + token + "}");

        return new ResultBean().ok(user.setPassword(null)).put("token", token);
    }
}