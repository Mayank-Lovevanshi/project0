package com.fastlearner.project0.service;

import com.fastlearner.project0.dto.problemTemplate.ProblemTemplateDTO;
import com.fastlearner.project0.enums.Language;

import java.util.List;

public interface ProblemTemplateService
{
    public ProblemTemplateDTO saveProblemTemplate(ProblemTemplateDTO problemTemplateDTO, Long problemId);
    public ProblemTemplateDTO updateProblemTemplate(ProblemTemplateDTO problemTemplateDTO, Long templateId, Long problemId);
    public ProblemTemplateDTO deleteProblemTemplate(Long templateId, Long problemId);
    public ProblemTemplateDTO getProblemTemplate(Language language, Long problemId);
    public List<ProblemTemplateDTO> getAllProblemTemplates(Long problemId);
}
