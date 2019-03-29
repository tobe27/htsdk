package com.lltech.system;

import com.lltech.common.utils.ResultBean;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CREATED BY L.C.Y on 2019-3-29
 */
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    @RequiresPermissions(value = {"hello_select", "hello_all"}, logical = Logical.OR)
    public ResultBean getHello() {
        return new ResultBean().ok("hello");
    }
}
