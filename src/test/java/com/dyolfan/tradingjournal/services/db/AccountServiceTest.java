package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.*;
import com.dyolfan.tradingjournal.data.user.Account;
import com.dyolfan.tradingjournal.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {
    private static final String ACCOUNT_ID = "1";
    private static final String STRATEGY_ID = "2";
    private static final String TRADE_ID = "3";
    private static final String ACCOUNT_NAME = "Main";
    private static final Currency ACCOUNT_CURRENCY = Currency.USD;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private StrategySearchService strategySearchService;
    @Mock
    private StrategyService strategyService;
    @Mock
    private TradeSearchService tradeSearchService;
    @Mock
    private TradeService tradeService;

    @Test
    void addAccount() {
        Account account = mock(Account.class);
        Account storedAccount = mock(Account.class);
        when(storedAccount.getId()).thenReturn(ACCOUNT_ID);

        Strategies storedStrategies = mock(Strategies.class);

        when(accountRepository.save(account)).thenReturn(storedAccount);
        when(strategySearchService.findStrategiesByAccountId(ACCOUNT_ID)).thenReturn(storedStrategies);

        accountService.addAccount(account);

        verify(storedAccount).setStrategies(storedStrategies);
    }

    @Test
    void getAccountById() {
        Account account = mock(Account.class);
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        Account result = accountService.getAccountById(ACCOUNT_ID);

        assertEquals(account, result);
    }

    @Test
    void getAccountByIdNotFound() {

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> accountService.getAccountById(ACCOUNT_ID));
    }

    @Test
    void findAccountById() {
        Account account = mock(Account.class);
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

        Optional<Account> result = accountService.findAccountById(ACCOUNT_ID);

        assertEquals(Optional.of(account), result);
    }

    @Test
    void updateAccountById() {
        Account account = new Account();
        account.setId(ACCOUNT_ID);
        account.setCurrency(ACCOUNT_CURRENCY);
        account.setName(ACCOUNT_NAME);
        Strategies strategies = mock(Strategies.class);
        Trades trades = mock(Trades.class);

        when(strategySearchService.findStrategiesByAccountId(ACCOUNT_ID)).thenReturn(strategies);
        when(tradeSearchService.findTradesByAccountId(ACCOUNT_ID)).thenReturn(trades);
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account result = accountService.updateAccountById(ACCOUNT_ID, account);

        Account expected = new Account();
        expected.setId(ACCOUNT_ID);
        expected.setName(ACCOUNT_NAME);
        expected.setCurrency(ACCOUNT_CURRENCY);
        expected.setTrades(trades);
        expected.setStrategies(strategies);

        assertEquals(expected, result);
    }

    @Test
    void updateAccountByIdAccountNotFound() {
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> accountService.updateAccountById(ACCOUNT_ID, new Account()));
    }

    @Test
    void deleteAccountById() {
        Strategy strategy = mock(Strategy.class);
        when(strategy.getId()).thenReturn(STRATEGY_ID);
        Trade trade = mock(Trade.class);
        when(trade.getId()).thenReturn(TRADE_ID);

        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.of(new Account()));

        when(strategySearchService.findStrategiesByAccountId(ACCOUNT_ID)).thenReturn(new Strategies(strategy));
        when(tradeSearchService.findTradesByAccountId(ACCOUNT_ID)).thenReturn(new Trades(trade));

        boolean result = accountService.deleteAccountById(ACCOUNT_ID);

        assertTrue(result);

        verify(strategyService).deleteStrategy(STRATEGY_ID, false);
        verify(tradeService).deleteTrade(TRADE_ID, false);
        verify(accountRepository).deleteById(ACCOUNT_ID);
    }

    @Test
    void deleteAccountByIdAccountNotFound() {
        when(accountRepository.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

        boolean result = accountService.deleteAccountById(ACCOUNT_ID);

        verify(accountRepository, times(0)).deleteById(ACCOUNT_ID);
        assertFalse(result);
    }
}