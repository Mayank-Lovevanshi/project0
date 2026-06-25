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
    @Column(nullable = false)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Parameter> parameters;
    @JoinColumn(name = "problem_id")
    @OneToOne(cascade = CascadeType.ALL)
    private Problem problem;
}
