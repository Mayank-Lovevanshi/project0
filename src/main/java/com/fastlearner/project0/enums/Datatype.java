package com.fastlearner.project0.enums;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Datatype
{
    private BaseType baseType;
    private int dimension;
    public boolean isPrimitive()
    {
        return dimension == 0;
    }
    public boolean isArray()
    {
        return dimension>1;
    }
}
