package com.example.line.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "line_history")
@Getter @Setter
public class LineHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long lineId;
    private String actionType; // 변경 유형 (ex: CREATE, UPDATE_PLAN, CANCEL)
    private LocalDateTime actionTime;

    // Getter/Setter ...
}
