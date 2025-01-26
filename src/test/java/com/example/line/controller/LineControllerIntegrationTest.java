package com.example.line.controller;

import com.example.line.LineServiceApplication;
import com.example.line.domain.Line;
import com.example.line.repository.LineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = LineServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LineControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        lineRepository.deleteAll();
    }

    @Test
    void testCreateAndGetLine() {
        Line line = new Line();
        line.setMemberId(1L);
        line.setLineNumber("010-5555-6666");
        line.setPlanName("BASIC");
        line.setStatus("ACTIVE");

        ResponseEntity<Line> createResp = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/lines",
                line,
                Line.class
        );
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        Line created = createResp.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getLineNumber()).isEqualTo("010-5555-6666");

        ResponseEntity<Line> getResp = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/lines/" + created.getId(),
                Line.class
        );
        assertThat(getResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResp.getBody().getPlanName()).isEqualTo("BASIC");
    }
}

