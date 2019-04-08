package com.lltech.system.config.handler;

import com.lltech.common.exception.BadRequestException;
import com.lltech.common.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

/**
 * @author jie
 * @date 2018-11-23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 唯一索引插入异常
     * @param e DuplicateKeyException 插入两次异常
     * @return ResultBean Code - 400
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultBean handleDuplicateKeyException(DuplicateKeyException e) {
        log.error("数据库中已存在该记录", e);
        return new ResultBean().error("数据库中已存在该记录");
    }

    /**
     * 拦截校验异常并返回ResultBean
     * @param e MethodArgumentNotValidException异常
     * @return ResultBean Code - 400
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean handleBindingException(MethodArgumentNotValidException e) {
        log.error("参数格式不正确", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder sb = new StringBuilder();
        FieldError fieldError = bindingResult.getFieldError();
        sb.append(Objects.requireNonNull(fieldError).getDefaultMessage());
        return new ResultBean().error(sb.toString());
    }

    /**
     * 接口请求方法错误
     * @param e HttpRequestMethodNotSupportedException 不支持的Http请求方法
     * @return ResultBean Code - 400
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultBean httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HTTP请求方式不支持", e);
        return new ResultBean().error("HTTP请求方式不支持");
    }

    /**
     * 拦截未授权的请求
     * @param e AuthorizationException Shiro未认证通过
     * @return ResultBean Code - 401
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public ResultBean handleUnAuthUserException(AuthorizationException e) {
        log.error("用户没有访问权限", e);
        return new ResultBean().error(HttpStatus.UNAUTHORIZED, "该用户没有访问权限");
    }

    /**
     * 自定义请求异常类
     * @param e BadRequestException 错误请求异常
     * @return ResultBean Code - 400
     */
    @ExceptionHandler(BadRequestException.class)
    public ResultBean handleBadRequestException(BadRequestException e) {
        log.error(e.getMessage(), e);
        return new ResultBean().error(e.getMessage());
    }

    /**
     * 拦截Exception后返回ResultBean
     * @param e Exception异常
     * @return ResultBean Code - 400
     */
    @ExceptionHandler(value = Exception.class)
    public ResultBean handleException(Exception e) {
        log.error("未知异常", e);
        return new ResultBean().error("未知异常，请联系客服: " + e.getMessage());
    }

}
