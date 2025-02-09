package com.example.line.service;

import com.example.line.domain.Line;
import com.example.line.repository.LineHistoryRepository;
import com.example.line.repository.LineRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class LineServiceTest {

    @Mock
    private LineRepository lineRepository;

    @Mock
    private LineHistoryRepository lineHistoryRepository;

    @InjectMocks
    private LineService lineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLine() {
        Line line = new Line();
        line.setLineNumber("010-1111-2222");

        when(lineRepository.save(any(Line.class))).thenAnswer(i -> {
            Line saved = i.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Line created = lineService.createLine(line);
        assertNotNull(created.getId());
    }

    @Test
    void testGetLine() {
        Line line = new Line();
        line.setId(10L);
        line.setLineNumber("010-3333-4444");
        when(lineRepository.findById(10L)).thenReturn(Optional.of(line));

        Line found = lineService.getLine(10L);
        assertNotNull(found);
        verify(lineRepository, times(1)).findById(10L);
    }
}

