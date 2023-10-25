package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.repositories.StrategyRepository;
import com.dyolfan.tradingjournal.services.sync.AccountSyncService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StrategyService {
    private final StrategyRepository strategyRepository;
    private final AccountSyncService accountSyncService;
    private final StrategySearchService strategySearchService;

    public StrategyService(StrategyRepository strategyRepository, AccountSyncService accountSyncService, StrategySearchService strategySearchService) {
        this.strategyRepository = strategyRepository;
        this.accountSyncService = accountSyncService;
        this.strategySearchService = strategySearchService;
    }

    public Strategy saveStrategy(Strategy strategy) {
        boolean isNew = strategy.getId() == null;
        Strategy storedStrategy = strategyRepository.save(strategy);

        accountSyncService.syncNewStrategies(storedStrategy.getAccountId(), new Strategies(storedStrategy));

        return storedStrategy;
    }

    public Strategy getStrategyById(String id) {
        return findStrategyById(id).orElseThrow();
    }

    public Optional<Strategy> findStrategyById(String id) {
        return strategyRepository.findById(id);
    }

    public Strategies findAllStrategyByIds(List<String> ids) {
        return new Strategies(strategyRepository.findAllById(ids));
    }

    public Strategy updateStrategy(String id, Strategy strategy) {
        return strategyRepository.findById(id).map(storedStrategy -> {
            storedStrategy.setDescription(strategy.getDescription());
            storedStrategy.setName(strategy.getName());
            storedStrategy.setSubtype(strategy.getSubtype());
            return storedStrategy;
        }).map(strategyRepository::save).orElseThrow();
    }

    public boolean deleteTrade(String id) {
        boolean isDeleted = false;

        try {
            String accountId = strategyRepository.findById(id).orElseThrow().getAccountId();
            strategyRepository.deleteById(id);
            isDeleted = true;
            accountSyncService.syncStrategies(accountId);
        } catch (Exception exception) {
            // TODO: add logging
        }
        return isDeleted;
    }
}
