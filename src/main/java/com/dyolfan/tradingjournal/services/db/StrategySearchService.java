package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.repositories.StrategyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StrategySearchService {
    private final StrategyRepository strategyRepository;

    public StrategySearchService(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    public Optional<Strategy> findStrategyById(String id) {
        return strategyRepository.findById(id);
    }

    public Strategies findAllStrategyByIds(List<String> ids) {
        return new Strategies(strategyRepository.findAllById(ids));
    }

    public Strategies findStrategiesByAccountId(String accountId) {
        return new Strategies(strategyRepository.findByAccountId(accountId));
    }
}
