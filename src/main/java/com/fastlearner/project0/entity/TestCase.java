package com.fastlearner.project0.entity;

import com.fastlearner.project0.enums.TestCaseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCase
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(nullable = false)
    private String inputData;
    @Lob
    @Column(nullable = false)
    private String expectedOutput;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestCaseType testCaseType;
    @Column(nullable = false)
    private Integer sequenceNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    private Problem problem;
}
