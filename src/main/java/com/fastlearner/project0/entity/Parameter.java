package com.fastlearner.project0.entity;

import com.fastlearner.project0.enums.BaseType;
import com.fastlearner.project0.enums.Datatype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Parameter
{
    private String name;
    @Embedded
    private Datatype type;
}
