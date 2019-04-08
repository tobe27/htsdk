package com.lltech.system.logging.aspect;

import com.lltech.common.utils.HttpContextUtils;
import com.lltech.common.utils.JwtUtils;
import com.lltech.common.utils.StringUtils;
import com.lltech.system.logging.model.LogDO;
import com.lltech.system.logging.repository.LogRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;


/**
 * 系统日志，切面处理类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017年3月8日 上午11:07:35
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
	private final LogRepository logRepository;

	@Autowired
	public LogAspect(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@Pointcut("@annotation(com.lltech.system.logging.annotation.Log)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;
		// 保存日志
		saveSysLog(point, time);

		return result;
	}

	/**
	 * 保存切面日志到数据库
	 * @param joinPoint 切面
	 * @param time 耗时
	 */
	private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		LogDO logEntity = new LogDO();
		com.lltech.system.logging.annotation.Log logAnnotation = method.getAnnotation(com.lltech.system.logging.annotation.Log.class);
		if(logAnnotation != null){
			// 注解上的描述
			logEntity.setDescription(logAnnotation.value());
		}

		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		logEntity.setMethod(className + "." + methodName + "()");

		// 请求的参数
		String params = "";
		Object[] argValues = joinPoint.getArgs();
		// 参数名称
		String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
		try{
			if (argValues != null) {
				for (int i = 0; i < argValues.length; i++) {
					params += argNames[i] + ":" + argValues[i] + ",";
				}
			}
		}catch (Exception e){
			log.error(e.getMessage(), e);
		}
		params = params.substring(0, params.length() == 0 ? 0 : params.length()-1);
		logEntity.setParams("{" + params + "}");

		// 获取request
		HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
		// 设置IP地址
		logEntity.setRequestIp(StringUtils.getIP(request));

		// 用户名
		String token =  getRequestToken(request);
		Claims claims;
		String username = (token == null || token.isEmpty())
				? "" : (claims = JwtUtils.parse(token)) == null
				? "" : claims.get("username") == null
				? "" : claims.get("username").toString();
		logEntity.setUsername(username);

		logEntity.setTime(time);
		logEntity.setLogType("INFO");
		logEntity.setGmtCreate(LocalDateTime.now());
		// 保存系统日志
		logRepository.save(logEntity);
	}

	/**
	 * 获取请求的token
	 * @param httpRequest req
	 * @return token
	 */
	private String getRequestToken(HttpServletRequest httpRequest){
		// 从header中获取token
		String token = httpRequest.getHeader("token");

		// 如果header中不存在token，则从参数中获取token
		if(StringUtils.isBlank(token)){
			token = httpRequest.getParameter("token");
		}

		return token;
	}

}
