package com.fastlearner.project0.repository;
import com.fastlearner.project0.entity.TestCase;
import com.fastlearner.project0.enums.TestCaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TestCaseRepository extends JpaRepository<TestCase,Long>
{
    Optional<List<TestCase>> findByProblemId(Long problemId);
    List<TestCase> findByProblemIdAndTestCaseType(Long problemId, TestCaseType testCaseType);
}
