package com.fastlearner.project0.entity;

import com.fastlearner.project0.enums.Difficulty;
import com.fastlearner.project0.enums.ProblemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Problem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String title;
    @Lob
    @Column(nullable = false)
    private String statement;
    @Column(nullable = false)
    private String constraints;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemStatus status;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "problem",cascade = CascadeType.ALL)
    private List<TestCase> testCases = new ArrayList<>();
    @OneToOne(mappedBy = "problem",cascade = CascadeType.ALL)
    private Structure structure;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "problem") // mappedBy bacha hai
    private List<Submission> submissions = new ArrayList<>();
}
