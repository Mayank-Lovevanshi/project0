package com.fastlearner.project0.dto.job;

import com.fastlearner.project0.dto.judge0.JudgeRequest;
import com.fastlearner.project0.dto.run.RunResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
@AllArgsConstructor
@Data
public class RunJob
{
    private Long problemId;
    private JudgeRequest judgeRequest;
    private CompletableFuture<RunResponseDTO> future;
}
