package com.fastlearner.project0.dto.submission;

import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.enums.Verdict;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmissionResponse
{
    private Long id;
    private Long problemId;
    private String problemTitle;
    private Language language;
    private Verdict verdict;
    private String executionTimeMs;
    private Integer memoryUsedKb;
    private Integer passedTestCases;
    private Integer totalTestCases;
    private LocalDateTime submittedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    private String errorMessage;
    private SubmissionStatus status;
}
    /*
Submission
-x Long id
-x String sourceCode
-x Language language
-x Verdict verdict
-x SubmissionStatus status
-x Double executionTimeMs
-x Long memoryUserKb
-x Integer passedTestCases
-x Integer totalTestCases
-x LocalDateTime submittedAt
-x String errorMessage
-x User user
-x Problem problem
-x String token
-x LocalDateTime startedAt
-x LocalDateTime completedAt
 */
