package com.fastlearner.project0.repository;

import com.fastlearner.project0.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem,Long> {
    //no methods required for now
}
