package com.lltech.system.logging.controller;

import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.logging.model.LogDO;
import com.lltech.system.logging.repository.LogRepository;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author CREATED BY L.C.Y on 2019-4-1
 */
@RestController
@RequestMapping("/system")
public class LogQueryController {
    private final LogRepository logRepository;

    @Autowired
    public LogQueryController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Log("查看日志记录")
    @GetMapping("/log/list")
    @RequiresPermissions("log_list")
    public ResultBean queryLogList(Integer pageNum, Integer pageSize) {
        Page<LogDO> page = logRepository.findAll(PageRequest.of(pageNum -1, pageSize, Sort.Direction.DESC, "id"));
        return new ResultBean().ok(page.getTotalElements(), page.getContent());
    }
}
