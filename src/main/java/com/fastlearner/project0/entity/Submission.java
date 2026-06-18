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
    private Verdict verdict;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status;
    private Double executionTimeMs;
    private Long memoryUsedKb;
    private Integer passedTestCases;
    private Integer totalTestCases;
    private LocalDateTime submittedAt;
    @Lob
    private String errorMessage;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name="user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "problem_id")
    private Problem problem;
}


