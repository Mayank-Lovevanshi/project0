package com.fastlearner.project0.repository;

import com.fastlearner.project0.entity.Problem;
import com.fastlearner.project0.entity.ProblemTemplate;
import com.fastlearner.project0.enums.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemTemplateRepository extends JpaRepository<ProblemTemplate,Long>
{
    Optional<ProblemTemplate> findByProblemAndLanguage(Problem problem, Language language);
    Optional<List<ProblemTemplate>> findAllByProblem(Problem problem);
}
