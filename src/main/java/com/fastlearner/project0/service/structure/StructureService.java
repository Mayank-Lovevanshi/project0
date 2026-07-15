package com.fastlearner.project0.service.structure;

import com.fastlearner.project0.dto.structure.AdminStructureResponse;
import com.fastlearner.project0.dto.structure.StructureDTO;
import com.fastlearner.project0.dto.structure.StudentStructureResponse;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.enums.Language;

public interface StructureService
{
     StructureDTO saveProblemTemplate(StructureDTO problemTemplate, Long problemId);
     StructureDTO updateProblemTemplate(StructureDTO structureRequestDTO, Long templateId, Long problemId);
     StructureDTO deleteProblemTemplate(Long templateId, Long problemId);
     AdminStructureResponse getProblemTemplate(Language language, Long problemId);
     Structure getStructure(Long problemId);
     StudentStructureResponse getStarterCode(Long problemId, Language language);
}
