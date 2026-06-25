package com.fastlearner.project0.repository;

import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.Structure;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StructureRepository extends JpaRepository<Structure, Long>
{
    public Optional<Structure> findByProblem(Problem problem);
}
