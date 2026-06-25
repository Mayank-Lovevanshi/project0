package com.fastlearner.project0.service.structure;

import com.fastlearner.project0.dto.structure.AdminStructureResponse;
import com.fastlearner.project0.dto.structure.StructureDTO;
import com.fastlearner.project0.dto.structure.StudentStructureResponse;
import com.fastlearner.project0.enums.Language;

public interface StructureService
{
    public StructureDTO saveProblemTemplate(StructureDTO problemTemplate, Long problemId);
    public StructureDTO updateProblemTemplate(StructureDTO structureRequestDTO, Long templateId, Long problemId);
    public StructureDTO deleteProblemTemplate(Long templateId, Long problemId);
    public AdminStructureResponse getProblemTemplate(Language language, Long problemId);
    public StudentStructureResponse getStarterCode(Long problemId, Language language);
}
