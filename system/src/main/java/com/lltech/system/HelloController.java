package com.lltech.system;

import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CREATED BY L.C.Y on 2019-3-29
 */
@RestController
@RequestMapping
public class HelloController {

    @GetMapping("/")
    @Log(value = "获取Hello")
    public ResultBean getHello() {
        return new ResultBean().ok("hello, v1.0");
    }
}
