package com.example.line.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "line_plans")
@Getter
@Setter
public class LinePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String planName;
    private int monthlyFee;
    private String description;

    // Getter/Setter ...
}
