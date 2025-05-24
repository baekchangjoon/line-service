package com.example.line.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "line")
@Getter @Setter
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;      // 가입 회원 ID
    private String lineNumber;  // 전화번호
    private String planName;    // 요금제
    private String status;      // ACTIVE, SUSPENDED, CANCELLED

    // Getter/Setter ...
}
