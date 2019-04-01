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
@RequestMapping("/system")
public class HelloController {

    @GetMapping("/hello")
    @Log(value = "获取Hello")
    @RequiresPermissions(value = {"hello_select", "hello_all"}, logical = Logical.OR)
    public ResultBean getHello() {
        return new ResultBean().ok("hello");
    }
}
