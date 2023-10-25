package com.dyolfan.tradingjournal.services.sync;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Trades;
import com.dyolfan.tradingjournal.data.user.Account;
import com.dyolfan.tradingjournal.repositories.AccountRepository;
import com.dyolfan.tradingjournal.services.db.StrategySearchService;
import com.dyolfan.tradingjournal.services.db.TradeSearchService;
import org.springframework.stereotype.Service;

@Service
public class AccountSyncService {
    private final AccountRepository accountRepository;
    private final StrategySearchService strategySearchService;
    private final TradeSearchService tradeSearchService;

    public AccountSyncService(AccountRepository accountRepository, StrategySearchService strategySearchService, TradeSearchService tradeSearchService) {
        this.accountRepository = accountRepository;
        this.strategySearchService = strategySearchService;
        this.tradeSearchService = tradeSearchService;
    }

    public Account syncNewTrades(String id, Trades trades) {
        return accountRepository.findById(id).map(storedAccount -> {
            storedAccount.getTrades().addAll(trades);
            return accountRepository.save(storedAccount);
        }).orElseThrow();
    }

    public Account syncTrades(String id) {
        Trades storedTrades = tradeSearchService.findTradesByAccountId(id);
        return accountRepository.findById(id).map(storedAccount -> {
            storedAccount.setTrades(storedTrades);
            return accountRepository.save(storedAccount);
        }).orElseThrow();
    }

    public Account syncNewStrategies(String id, Strategies strategies) {
        return accountRepository.findById(id).map(storedAccount -> {
            storedAccount.getStrategies().addAll(strategies);
            return accountRepository.save(storedAccount);
        }).orElseThrow();
    }

    public Account syncStrategies(String id) {
        Strategies storedStrategies = strategySearchService.findStrategiesByAccountId(id);
        return accountRepository.findById(id).map(storedAccount -> {
                    storedAccount.setStrategies(storedStrategies);
                    return accountRepository.save(storedAccount);
                }
        ).orElseThrow();
    }
}
