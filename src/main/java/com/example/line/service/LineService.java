package com.example.line.service;

import com.example.line.domain.Line;
import com.example.line.domain.LineHistory;
import com.example.line.repository.LineHistoryRepository;
import com.example.line.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LineService {

    @Autowired
    private LineRepository lineRepository;

    @Autowired
    private LineHistoryRepository lineHistoryRepository;

    public Line createLine(Line line) {
        Line saved = lineRepository.save(line);
        recordHistory(saved.getId(), "CREATE");
        return saved;
    }

    public Line getLine(Long id) {
        return lineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Line not found"));
    }

    public List<Line> getAllLines() {
        return lineRepository.findAll();
    }

    public Line updateLine(Long id, Line updated) {
        Line existing = getLine(id);
        existing.setPlanName(updated.getPlanName());
        existing.setStatus(updated.getStatus());
        Line saved = lineRepository.save(existing);
        recordHistory(saved.getId(), "UPDATE");
        return saved;
    }

    public void cancelLine(Long id) {
        Line existing = getLine(id);
        existing.setStatus("CANCELLED");
        lineRepository.save(existing);
        recordHistory(id, "CANCEL");
    }

    public void recordHistory(Long lineId, String action) {
        LineHistory lh = new LineHistory();
        lh.setLineId(lineId);
        lh.setActionType(action);
        lh.setActionTime(LocalDateTime.now());
        lineHistoryRepository.save(lh);
    }
}
