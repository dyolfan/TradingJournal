package com.dyolfan.tradingjournal.services.sync;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.data.trade.Trade;
import com.dyolfan.tradingjournal.data.trade.Trades;
import com.dyolfan.tradingjournal.data.user.Account;
import com.dyolfan.tradingjournal.repositories.AccountRepository;
import com.dyolfan.tradingjournal.services.db.StrategySearchService;
import com.dyolfan.tradingjournal.services.db.TradeSearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountSyncServiceTest {
    public static final String ACCOUNT_ID = "1";

    @InjectMocks
    private AccountSyncService accountSyncService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private StrategySearchService strategySearchService;
    @Mock
    private TradeSearchService tradeSearchService;

    @Test
    void syncNewTrades() {
        Trade trade = new Trade();
        Trade trade2 = new Trade();
        Trade trade3 = new Trade();
        Trades trades = new Trades(trade2, trade3);

        Account storedAccount = new Account();
        storedAccount.getTrades().add(trade);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(storedAccount));

        Account expectedAccount = new Account();
        Trades expectedTrades = new Trades(trade, trade2, trade3);
        expectedAccount.getTrades().addAll(expectedTrades);
        when(accountRepository.save(eq(expectedAccount))).thenReturn(expectedAccount);

        Account result = accountSyncService.syncNewTrades(ACCOUNT_ID, trades);

        assertEquals(expectedTrades, result.getTrades());
        assertEquals(expectedAccount, result);
    }

    @Test
    void syncTrades() {
        Trade trade = new Trade();
        Trade trade2 = new Trade();
        Trades trades = new Trades(trade, trade2);

        Account storedAccount = new Account();

        when(tradeSearchService.findTradesByAccountId(ACCOUNT_ID)).thenReturn(trades);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(storedAccount));

        Account expectedAccount = new Account();
        expectedAccount.getTrades().addAll(trades);
        when(accountRepository.save(eq(expectedAccount))).thenReturn(expectedAccount);

        Account result = accountSyncService.syncTrades(ACCOUNT_ID);

        assertEquals(trades, result.getTrades());
        assertEquals(expectedAccount, result);
    }


    @Test
    void syncNewStrategies() {
        Strategy strategy = new Strategy();
        Strategy strategy2 = new Strategy();
        Strategy strategy3 = new Strategy();
        Strategies strategies = new Strategies(strategy2, strategy3);

        Account storedAccount = new Account();
        storedAccount.getStrategies().add(strategy);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(storedAccount));

        Account expectedAccount = new Account();
        Strategies expectedStrategies = new Strategies(strategy, strategy2, strategy3);
        expectedAccount.getStrategies().addAll(expectedStrategies);
        when(accountRepository.save(eq(expectedAccount))).thenReturn(expectedAccount);

        Account result = accountSyncService.syncNewStrategies(ACCOUNT_ID, strategies);

        assertEquals(expectedStrategies, result.getStrategies());
        assertEquals(expectedAccount, result);
    }

    @Test
    void syncStrategies() {
        Strategy strategy = new Strategy();
        Strategy strategy2 = new Strategy();
        Strategies strategies = new Strategies(strategy, strategy2);

        Account storedAccount = new Account();

        when(strategySearchService.findStrategiesByAccountId(ACCOUNT_ID)).thenReturn(strategies);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(storedAccount));

        Account expectedAccount = new Account();
        expectedAccount.getStrategies().addAll(strategies);
        when(accountRepository.save(eq(expectedAccount))).thenReturn(expectedAccount);

        Account result = accountSyncService.syncStrategies(ACCOUNT_ID);

        assertEquals(strategies, result.getStrategies());
        assertEquals(expectedAccount, result);
    }
}