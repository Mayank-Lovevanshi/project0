package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.structure.AdminStructureResponse;
import com.fastlearner.project0.dto.structure.StructureDTO;
import com.fastlearner.project0.dto.structure.StudentStructureResponse;
import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.structure.StructureService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/problems/{problemId}/templates")
//@RequestMapping("/api/templates")
public class StructureController
{
    private final StructureService problemTemplateService;
    public StructureController(StructureService problemTemplateService)
    {
        this.problemTemplateService = problemTemplateService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StructureDTO> saveProblemTemplate(@RequestBody @Valid StructureDTO structure, @PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.saveProblemTemplate(structure,problemId), HttpStatus.CREATED);
    }
    @PutMapping("/{templateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StructureDTO> updateProblemTemplate(@RequestBody @Valid StructureDTO structureDTO,
                                                                    @PathVariable Long templateId, @PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.updateProblemTemplate(structureDTO,templateId,problemId),HttpStatus.OK);
    }
    @DeleteMapping("/{templateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StructureDTO> deleteProblemTemplate(@PathVariable Long templateId,@PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.deleteProblemTemplate(templateId,problemId),HttpStatus.OK);
    }
    @GetMapping("/{language}/admi")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStructureResponse> getProblemTemplate(@PathVariable Language language, @PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.getProblemTemplate(language,problemId),HttpStatus.OK);
    }
    @GetMapping("/{language}")
    public ResponseEntity<StudentStructureResponse> getStarterCode(@PathVariable Long problemId,@PathVariable Language language)
    {
        return new ResponseEntity<>(problemTemplateService.getStarterCode(problemId,language),HttpStatus.OK);
    }
}
