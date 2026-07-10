package com.fastlearner.project0.serviceImpl.execution;
import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.service.execution.ExecutionService;
import com.fastlearner.project0.service.execution.queue.ExecutionQueue;
import com.fastlearner.project0.service.judge.JudgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ExecutionServiceImpl implements ExecutionService
{
    private final ExecutionQueue executionQueue;
    private final JudgeService judgeService;
    public void execute() throws InterruptedException {
        List<Job<?>> jobs = new ArrayList<>();
        while(!executionQueue.isEmpty())
        {
            jobs.add(executionQueue.take());
        }
        judgeService.executeBatch(jobs);
    }
}
/*
extra Queue ki zarurat kyu padi ThreadPoolExecutor bhi to ek queue maintain krta hai
Because mujhe chaiye ki me request ke batches banau 10ms tk wait kru aur requests ka
 */