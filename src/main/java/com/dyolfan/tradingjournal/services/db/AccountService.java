package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.user.Account;
import com.dyolfan.tradingjournal.repositories.AccountRepository;
import com.dyolfan.tradingjournal.validators.NewAccountValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountsRepository;
    private final StrategySearchService strategySearchService;
    private final StrategyService strategyService;
    private final TradeService tradeService;
    private final TradeSearchService tradeSearchService;
    private final NewAccountValidator validator;

    public AccountService(AccountRepository accountsRepository, StrategySearchService strategySearchService, StrategyService strategyService, TradeService tradeService, TradeSearchService tradeSearchService, NewAccountValidator validator) {
        this.accountsRepository = accountsRepository;
        this.strategySearchService = strategySearchService;
        this.strategyService = strategyService;
        this.tradeService = tradeService;
        this.tradeSearchService = tradeSearchService;
        this.validator = validator;
    }

    public Account addAccount(Account account)  {
        boolean isValid = validator.validateNewAccount(account);
        if(!isValid) {
            throw new RuntimeException("Account already exists");
        }

        Account storedAccount = accountsRepository.save(account);

        Strategies strategies = strategySearchService.findStrategiesByAccountId(storedAccount.getId());
        storedAccount.setStrategies(strategies);

        return storedAccount;
    }

    public Account getAccountById(String id) {
        return findAccountById(id).orElseThrow();
    }

    public Optional<Account> findAccountById(String id) {
        return accountsRepository.findById(id);
    }

    public Account getAccountByName(String accountName) {
        return accountsRepository.findByName(accountName).orElseThrow();
    }

    public String getAccountIdByName(String accountName) {
        return accountsRepository.findByName(accountName).orElseThrow().getId();
    }

    public Account updateAccountById(String id, Account account) {
        return findAccountById(id).map(storedAccount -> {
            storedAccount.setName(account.getName());
            storedAccount.setCurrency(account.getCurrency());
            return accountsRepository.save(storedAccount);
        }).map(storedAccount -> {
            storedAccount.setStrategies(strategySearchService.findStrategiesByAccountId(id));
            storedAccount.setTrades(tradeSearchService.findTradesByAccountId(id));
            return storedAccount;
        }).orElseThrow();
    }

    public boolean deleteAccountById(String id) {
        boolean isDeleted = false;
        try {
            accountsRepository.findById(id).orElseThrow();

            tradeSearchService.findTradesByAccountId(id).forEach(trade ->
                    tradeService.deleteTrade(trade.getId(), false)
            );
            strategySearchService.findStrategiesByAccountId(id).forEach(strategy ->
                    strategyService.deleteStrategy(strategy.getId(), false)
            );

            accountsRepository.deleteById(id);
            isDeleted = true;
        } catch (Exception exception) {
            // TODO: add logging
        }
        return isDeleted;
    }
}
