package com.fastlearner.project0.controller;

import com.fastlearner.project0.dto.structure.StructureRequestDTO;
import com.fastlearner.project0.entity.Structure;
import com.fastlearner.project0.service.CodeGeneratorService;
import com.fastlearner.project0.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/template")
public class CodeGeneratorController
{
    private final CodeGeneratorService codeGeneratorService;
    private final TemplateService templateService;
    public CodeGeneratorController(CodeGeneratorService codeGeneratorService, TemplateService templateService) {
        this.codeGeneratorService = codeGeneratorService;
        this.templateService = templateService;
    }
    @PostMapping
    public ResponseEntity<String>  saveTemplate(@RequestBody @Valid StructureRequestDTO structure)
    {
        return new ResponseEntity<>(templateService.saveTemplate(structure), HttpStatus.OK);
    }
    @PostMapping("/viewJava")
    public ResponseEntity<String>  generateJavaCode(@RequestBody Structure structure)
    {
        return new ResponseEntity<>(codeGeneratorService.generateJavaStarterCode(structure), HttpStatus.OK);
    }
    @PostMapping("/viewJavaDriver")
    public ResponseEntity<String> generateDriverJavaCode(@RequestBody Structure structure)
    {
        return new ResponseEntity<>(codeGeneratorService.generateJavaDriverCode(structure), HttpStatus.OK);
    }
}
