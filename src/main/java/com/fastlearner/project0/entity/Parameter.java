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
@Entity
@Table(name = "parameters")
public class Parameter
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String name;
    @Embedded
    private Datatype type;
}
