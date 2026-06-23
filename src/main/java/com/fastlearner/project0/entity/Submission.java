package com.fastlearner.project0.entity;

import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.enums.SubmissionStatus;
import com.fastlearner.project0.enums.Verdict;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Submission
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(nullable = false)
    private String sourceCode;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Verdict verdict;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status;
    private String executionTimeMs;
    private Integer memoryUsedKb;
    private Integer passedTestCases;
    private Integer totalTestCases;
    private LocalDateTime submittedAt;
    private String token;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
    @Lob
    private String errorMessage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "problem_id")
    private Problem problem;
}

/*
Submission
-Long id
-String sourceCode
-Language language
-Verdict verdict
-SubmissionStatus status
-Double executionTimeMs
-Long memoryUserKb
-Integer passedTestCases
-Integer totalTestCases
-LocalDateTime submittedAt
-String errorMessage
-User user
-Problem problem
-String token
-LocalDateTime startedAt
-LocalDateTime completedAt
 */


