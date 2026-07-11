package com.fastlearner.project0.serviceImpl.execution;
import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.service.execution.ExecutionService;
import com.fastlearner.project0.service.execution.queue.ExecutionQueue;
import com.fastlearner.project0.service.execution.registery.PendingExecutionRegistry;
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
    private final PendingExecutionRegistry pendingExecutionRegistry;
    public void execute() throws InterruptedException {
        List<Job<?>> jobs = new ArrayList<>();
        while(!executionQueue.isEmpty())
        {
            jobs.add(executionQueue.take());
        }
        Judge0TokenResponse[] tokens = judgeService.executeBatch(jobs);
        for(int i=0;i<tokens.length;i++)
        {
            pendingExecutionRegistry.put(tokens[i].getToken(),jobs.get(i));
        }
    }
}
