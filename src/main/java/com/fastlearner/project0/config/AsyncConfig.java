
package com.fastlearner.project0.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "judgeTaskExecutor")
    public TaskExecutor judgeTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);       // Keeps 10 threads always ready
        executor.setMaxPoolSize(25);        // Scales up to 25 threads under high load
        executor.setQueueCapacity(100);     // Holds up to 100 pending batch jobs
        executor.setThreadNamePrefix("JudgeTask-");
        executor.initialize();
        return executor;
    }
}
