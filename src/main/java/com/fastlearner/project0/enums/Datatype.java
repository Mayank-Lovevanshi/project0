package com.fastlearner.project0.enums;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Datatype
{
    @Enumerated(EnumType.STRING)
    private BaseType baseType;
    private Integer dimensions;
    public boolean isPrimitive()
    {
        return dimensions == 0;
    }
    public boolean isArray()
    {
        return dimensions>=1;
    }
}
