package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.repositories.StrategyRepository;
import com.dyolfan.tradingjournal.services.sync.AccountSyncService;
import org.springframework.stereotype.Service;

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
        Strategy storedStrategy = strategyRepository.save(strategy);

        accountSyncService.syncNewStrategies(storedStrategy.getAccountId(), new Strategies(storedStrategy));

        return storedStrategy;
    }

    public Strategy getStrategyById(String id) {
        return strategySearchService.findStrategyById(id).orElseThrow();
    }

    public Strategy updateStrategy(String id, Strategy strategy) {
        return strategyRepository.findById(id).map(storedStrategy -> {
            storedStrategy.setDescription(strategy.getDescription());
            storedStrategy.setName(strategy.getName());
            storedStrategy.setTags(strategy.getTags());
            storedStrategy.setTags(strategy.getTags());
            return storedStrategy;
        }).map(strategyRepository::save).orElseThrow();
    }

    public boolean deleteStrategy(String id) {
        return deleteStrategy(id, true);
    }

    public boolean deleteStrategy(String id, boolean syncAccount) {
        boolean isDeleted = false;

        try {
            Strategy strategy = strategyRepository.findById(id).orElseThrow();
            strategyRepository.deleteById(id);
            isDeleted = true;
            if (syncAccount) {
                accountSyncService.syncStrategies(strategy.getAccountId());
            }

        } catch (
                Exception exception) {
            // TODO: add logging
        }
        return isDeleted;
    }
}
