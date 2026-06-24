package com.fastlearner.project0.entity;

import com.fastlearner.project0.enums.Datatype;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "structures")
public class Structure
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    private String methodName;
    @Column(nullable = false)
    private Datatype returnType;
    @OneToMany(cascade = CascadeType.ALL)
    @Column(nullable = false)
    private List<Parameter> parameters;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "structure")
    private Problem problem;
}
