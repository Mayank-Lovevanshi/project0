package com.fastlearner.project0.dto.structure;

import com.fastlearner.project0.entity.Parameter;
import com.fastlearner.project0.enums.Datatype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StructureRequestDTO
{
    private Long problemId;
    private String methodName;
    private Datatype returnType;
    private List<Parameter> parameters;
}
