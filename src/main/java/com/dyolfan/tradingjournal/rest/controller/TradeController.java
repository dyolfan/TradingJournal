package com.dyolfan.tradingjournal.rest.controller;

import com.dyolfan.tradingjournal.data.trade.Trade;
import com.dyolfan.tradingjournal.services.db.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("trades")
public class TradeController {
    private final TradeService tradesService;

    public TradeController(@Autowired TradeService tradesService) {
        this.tradesService = tradesService;
    }

    @PostMapping("/add")
    public ResponseEntity<Trade> addTrade(@Valid @RequestBody Trade trade) {
        return ResponseEntity.ok(tradesService.saveTrade(trade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Trade> getTradeById(@PathVariable String id) {
        return ResponseEntity.ok(tradesService.getTradeById(id));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Trade> updateTrade(@PathVariable String id, @Valid @RequestBody Trade trade) {
        return ResponseEntity.ok(tradesService.updateTrade(id, trade));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTrade(@PathVariable String id) {
        return ResponseEntity.ok(tradesService.deleteTrade(id));
    }
}
