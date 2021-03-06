package com.lltech.common.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author jie
 * @date 2019-01-08
 */
@Configuration
public class ThreadPoolUtil {

    @Bean
    public ExecutorService getThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        int size = 5;
        return new ThreadPoolExecutor(size, size,0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), namedThreadFactory);
    }
}
