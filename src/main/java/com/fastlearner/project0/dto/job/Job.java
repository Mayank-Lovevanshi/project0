package com.fastlearner.project0.dto.job;

import com.fastlearner.project0.dto.judge0.JudgeRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.CompletableFuture;
@AllArgsConstructor
@Data
public class Job <T>
{
    private JudgeRequest judgeRequest;
    private CompletableFuture<T> future;
}
