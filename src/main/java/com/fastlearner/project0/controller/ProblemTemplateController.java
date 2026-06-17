package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.problemTemplate.ProblemTemplateDTO;

import com.fastlearner.project0.enums.Language;
import com.fastlearner.project0.service.ProblemTemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems/{problemId}/templates")
public class ProblemTemplateController
{
    private final ProblemTemplateService problemTemplateService;
    public ProblemTemplateController(ProblemTemplateService problemTemplateService)
    {
        this.problemTemplateService = problemTemplateService;
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemTemplateDTO> saveProblemTemplate(@RequestBody @Valid ProblemTemplateDTO problemTemplateDTO, @PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.saveProblemTemplate(problemTemplateDTO,problemId), HttpStatus.CREATED);
    }
    @PutMapping("/{templateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemTemplateDTO> updateProblemTemplate(@RequestBody @Valid ProblemTemplateDTO problemTemplateDTO,
                                                                    @PathVariable Long templateId,@PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.updateProblemTemplate(problemTemplateDTO,templateId,problemId),HttpStatus.OK);
    }
    @DeleteMapping("/{templateId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemTemplateDTO> deleteProblemTemplate(@PathVariable Long templateId,@PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.deleteProblemTemplate(templateId,problemId),HttpStatus.OK);
    }
    @GetMapping("/{language}")
    public ResponseEntity<ProblemTemplateDTO> getProblemTemplate(@PathVariable Language language, @PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.getProblemTemplate(language,problemId),HttpStatus.OK);
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProblemTemplateDTO>> getAllProblemTemplates(@PathVariable Long problemId)
    {
        return new ResponseEntity<>(problemTemplateService.getAllProblemTemplates(problemId),HttpStatus.OK);
    }
}
