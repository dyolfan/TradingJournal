package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.user.Account;
import com.dyolfan.tradingjournal.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountsRepository;
    private final StrategySearchService strategySearchService;
    private final TradeSearchService tradeSearchService;
    private final TradeService tradeService;

    public AccountService(AccountRepository accountsRepository, StrategySearchService strategySearchService, TradeSearchService tradeSearchService, TradeService tradeService) {
        this.accountsRepository = accountsRepository;
        this.strategySearchService = strategySearchService;
        this.tradeSearchService = tradeSearchService;
        this.tradeService = tradeService;
    }

    public Account addAccount(Account account) {
        Account storedAccount = accountsRepository.save(account);

        Strategies strategies = strategySearchService.findAllStrategyByIds(storedAccount.getStrategies().getIds());
        storedAccount.setStrategies(strategies);

        return storedAccount;
    }

    public Account getAccountById(String id) {
        return findAccountById(id).orElseThrow();
    }

    public Optional<Account> findAccountById(String id) {
        return accountsRepository.findById(id);
    }

    public Account updateAccountById(String id, Account account) {
        return findAccountById(id).map(storedAccount -> {
            storedAccount.setName(account.getName());
            storedAccount.setCurrency(account.getCurrency());
            storedAccount.setTrades(account.getTrades());
            storedAccount.setStrategies(account.getStrategies());
            return accountsRepository.save(storedAccount);
        }).map(storedAccount -> {
            storedAccount.setStrategies(strategySearchService.findAllStrategyByIds(storedAccount.getStrategies().getIds()));
            storedAccount.setTrades(tradeSearchService.findAllTradeByIds(storedAccount.getStrategies().getIds()));
            return storedAccount;
        }).orElseThrow();
    }

    public boolean deleteAccountById(String id) {
        boolean isDeleted = false;
        try {
            accountsRepository.deleteById(id);
            isDeleted = true;
        } catch (Exception exception) {
            // TODO: add logging
        }
        return isDeleted;
    }
}
