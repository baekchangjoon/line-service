package com.example.line.controller;

import com.example.line.domain.Line;
import com.example.line.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lines")
public class LineController {

    @Autowired
    private LineService lineService;

    @PostMapping
    public Line createLine(@RequestBody Line line) {
        return lineService.createLine(line);
    }

    @GetMapping("/{id}")
    public Line getLine(@PathVariable Long id) {
        return lineService.getLine(id);
    }

    @GetMapping
    public List<Line> getAllLines() {
        return lineService.getAllLines();
    }

    @PutMapping("/{id}")
    public Line updateLine(@PathVariable Long id, @RequestBody Line updated) {
        return lineService.updateLine(id, updated);
    }

    @PutMapping("/{id}/cancel")
    public void cancelLine(@PathVariable Long id) {
        lineService.cancelLine(id);
    }

    // 추가 API(요금제 변경, 일시정지, 재개통 등)도 유사하게 작성 가능
}
