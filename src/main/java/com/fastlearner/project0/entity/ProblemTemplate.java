package com.fastlearner.project0.entity;

import com.fastlearner.project0.enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemTemplate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    private Language language;
    @Lob
    private String starterCode;
    @Lob
    private String driverCode;
    @ManyToOne
    private Problem problem;
}
