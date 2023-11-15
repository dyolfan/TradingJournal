package com.dyolfan.tradingjournal.rest.controller;

import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.services.db.StrategyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("strategies")
@Transactional
public class StrategyController {
    private final StrategyService strategiesService;

    public StrategyController(@Autowired StrategyService strategiesService) {
        this.strategiesService = strategiesService;
    }

    @PostMapping("/add")
    public ResponseEntity<Strategy> addTrade(@Valid @RequestBody Strategy strategy) {
        return ResponseEntity.ok(strategiesService.saveStrategy(strategy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Strategy> getTradeById(@PathVariable String id) {
        return ResponseEntity.ok(strategiesService.getStrategyById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Strategy> updateTrade(@PathVariable String id, @Valid @RequestBody Strategy strategy) {
        return ResponseEntity.ok(strategiesService.updateStrategy(id, strategy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTrade(@PathVariable String id) {
        return ResponseEntity.ok(strategiesService.deleteStrategy(id));
    }
}
