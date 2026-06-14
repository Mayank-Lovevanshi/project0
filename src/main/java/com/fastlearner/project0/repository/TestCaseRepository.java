package com.fastlearner.project0.repository;
import com.fastlearner.project0.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestCaseRepository extends JpaRepository<TestCase,Long>
{
    Optional<List<TestCase>> findByProblemId(Long problemId);
}
