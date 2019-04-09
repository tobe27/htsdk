package com.lltech.system.quartz.controller;

import com.lltech.common.exception.BadRequestException;
import com.lltech.common.utils.ResultBean;
import com.lltech.system.logging.annotation.Log;
import com.lltech.system.quartz.model.QuartzJob;
import com.lltech.system.quartz.model.QuartzLog;
import com.lltech.system.quartz.service.QuartzJobService;
import com.lltech.system.quartz.service.query.QuartzJobQueryService;
import com.lltech.system.quartz.service.query.QuartzLogQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author jie
 * @date 2019-01-07
 */
@Slf4j
@RestController
@RequestMapping("/system")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";

    private final QuartzJobService quartzJobService;
    private final QuartzJobQueryService quartzJobQueryService;
    private final QuartzLogQueryService quartzLogQueryService;

    @Autowired
    public QuartzJobController(QuartzJobService quartzJobService, QuartzJobQueryService quartzJobQueryService, QuartzLogQueryService quartzLogQueryService) {
        this.quartzJobService = quartzJobService;
        this.quartzJobQueryService = quartzJobQueryService;
        this.quartzLogQueryService = quartzLogQueryService;
    }

    @Log("定时任务列表")
    @GetMapping(value = "/job/list")
    public ResultBean getJobs(QuartzJob resources, Integer pageNum, Integer pageSize){
        Page page = quartzJobQueryService.queryAll(resources,PageRequest.of(pageNum -1, pageSize, Sort.Direction.DESC, "id"));
        return new ResultBean().ok(page.getTotalElements(), page.getContent());
    }

    /**
     * 查询定时任务日志列表
     * @param resources
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Log("定时任务日志列表")
    @GetMapping(value = "/job/log/list")
    public ResultBean getJobLogs(QuartzLog resources, Integer pageNum, Integer pageSize){
        Page page = quartzLogQueryService.queryAll(resources, PageRequest.of(pageNum -1, pageSize, Sort.Direction.DESC, "id"));
        return new ResultBean().ok(page.getTotalElements(), page.getContent());
    }

    @Log("新增定时任务")
    @PostMapping(value = "/job")
    public ResultBean create(@Validated @RequestBody QuartzJob resources){
        if (resources.getId() != null) {
            throw new BadRequestException("A new "+ ENTITY_NAME +" cannot already have an ID");
        }
        quartzJobService.create(resources);
        return new ResultBean().ok();
    }

    @Log("修改定时任务")
    @PutMapping(value = "/job")
    public ResultBean update(@Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources){
        quartzJobService.update(resources);
        return new ResultBean().ok();
    }

    @Log("更改定时任务状态")
    @PutMapping(value = "/job/{id}")
    public ResultBean updateIsPause(@PathVariable Long id){
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return new ResultBean().ok();
    }

    /**
     * 立即执行一次定时任务
     * @param id 任务id
     * @return code- 204, message- NO_CONTENT
     */
    @Log("执行一次定时任务")
    @PutMapping(value = "/job/{id}/exec")
    public ResultBean execution(@PathVariable Long id){
        quartzJobService.execution(quartzJobService.findById(id));
        return new ResultBean().ok();
    }

    @Log("删除定时任务")
    @DeleteMapping(value = "/job/{id}")
    public ResultBean delete(@PathVariable Long id){
        quartzJobService.delete(quartzJobService.findById(id));
        return new ResultBean().ok();
    }
}
