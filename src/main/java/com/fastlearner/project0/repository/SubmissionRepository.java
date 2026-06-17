package com.fastlearner.project0.repository;

import com.fastlearner.project0.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
