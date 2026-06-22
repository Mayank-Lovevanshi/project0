package com.fastlearner.project0.repository;

import com.fastlearner.project0.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long>
{
    @Query(nativeQuery = true,value = "SELECT * FROM submission WHERE status = 'PENDING' ORDER BY submitted_at ASC LIMIT 10")
    List<Submission> findSubmissionsToJudge();
    @Query(value = "SELECT *FROM submissionsWHERE id IN (:ids)", nativeQuery = true)
    List<Submission> findByIds(@Param("ids") List<Long> ids);
    @Query(nativeQuery = true,value = "Select * from submission where token=(:token)")
    Optional<Submission> findByToken(@Param("token")String token);
}
