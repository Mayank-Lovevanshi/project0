package com.fastlearner.project0.serviceImpl.run;
import com.fastlearner.project0.dto.job.Job;
import com.fastlearner.project0.dto.run.RunRequestDTO;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import com.fastlearner.project0.exceptions.AuthenticationException;
import com.fastlearner.project0.service.execution.queue.ExecutionQueue;
import com.fastlearner.project0.service.run.RunService;
import com.fastlearner.project0.serviceImpl.job.RunJobFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class RunServiceImpl implements RunService
{
    private final RunJobFactory runJobFactory;
    private final ExecutionQueue executionQueue;
    @Override
    public CompletableFuture<RunResponseDTO> run(RunRequestDTO runRequest) {
        Long problemId = runRequest.getProblemId();
        if(problemId == null || problemId <= 0) throw new IllegalArgumentException("INVALID_PROBLEM_ID");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) throw new AuthenticationException("AUTHENTICATION_NEEDED");
        Job<RunResponseDTO> job = runJobFactory.createJob(runRequest);
        executionQueue.enqueue(job);
        return job.getFuture();
    }


}
/*
finalCode
language
input
 */