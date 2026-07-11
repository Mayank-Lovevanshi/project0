package com.fastlearner.project0.serviceImpl.run.execution;
import com.fastlearner.project0.dto.job.RunJob;
import com.fastlearner.project0.dto.judge0.Judge0TokenResponse;
import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.service.judge.JudgeService;
import com.fastlearner.project0.service.run.execution.RunExecutionService;
import com.fastlearner.project0.service.run.queue.RunExecutionQueue;
import com.fastlearner.project0.service.run.registery.PendingRunExecutionRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RunExecutionServiceImpl implements RunExecutionService
{
    private final RunExecutionQueue executionQueue;
    private final JudgeService judgeService;
    private final PendingRunExecutionRegistry pendingExecutionRegistry;
    public void execute() throws InterruptedException {
        List<RunJob> jobs = new ArrayList<>();
        while(!executionQueue.isEmpty())
        {
            jobs.add(executionQueue.take());
        }
        List<JudgeRequest> judgeRequests = jobs.stream().map(job -> job.getJudgeRequest()).toList();
        Judge0TokenResponse[] tokens = judgeService.execute(judgeRequests);
        for(int i=0;i<tokens.length;i++)
        {
            pendingExecutionRegistry.put(tokens[i].getToken(),jobs.get(i));
        }
    }
}
