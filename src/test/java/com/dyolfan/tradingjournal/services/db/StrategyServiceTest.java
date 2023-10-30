package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.repositories.StrategyRepository;
import com.dyolfan.tradingjournal.services.sync.AccountSyncService;
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
class StrategyServiceTest {
    public static final String ACCOUNT_ID = "1";
    public static final String STRATEGY_ID = "2";
    private static final String STRATEGY_NAME = "ICT";
    private static final String STRATEGY_SUBTYPE = "London Sweep";
    private static final String STRATEGY_DESCRIPTION = "Gain profits";

    @InjectMocks
    private StrategyService strategyService;

    @Mock
    private StrategyRepository strategyRepository;
    @Mock
    private AccountSyncService accountSyncService;
    @Mock
    private StrategySearchService strategySearchService;

    @Test
    void saveStrategy() {
        Strategy strategy = mock(Strategy.class);
        Strategy storedStrategy = mock(Strategy.class);
        when(storedStrategy.getAccountId()).thenReturn(ACCOUNT_ID);

        when(strategyRepository.save(strategy)).thenReturn(storedStrategy);

        Strategy result = strategyService.saveStrategy(strategy);

        assertEquals(storedStrategy, result);
        verify(accountSyncService).syncNewStrategies(ACCOUNT_ID, new Strategies(storedStrategy));
    }

    @Test
    void getStrategyById() {
        Strategy strategy = mock(Strategy.class);

        when(strategySearchService.findStrategyById(STRATEGY_ID)).thenReturn(Optional.of(strategy));

        Strategy result = strategyService.getStrategyById(STRATEGY_ID);

        assertEquals(strategy, result);
    }

    @Test
    void getStrategyByIdNotFound() {
        when(strategySearchService.findStrategyById(STRATEGY_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> strategyService.getStrategyById(STRATEGY_ID));
    }


    @Test
    void updateStrategy() {
        Strategy strategy = mock(Strategy.class);
        when(strategy.getName()).thenReturn(STRATEGY_NAME);
        when(strategy.getSubtype()).thenReturn(STRATEGY_SUBTYPE);
        when(strategy.getDescription()).thenReturn(STRATEGY_DESCRIPTION);

        Strategy storedStrategy = new Strategy();

        when(strategyRepository.findById(STRATEGY_ID)).thenReturn(Optional.of(storedStrategy));
        when(strategyRepository.save(storedStrategy)).thenReturn(storedStrategy);

        Strategy result = strategyService.updateStrategy(STRATEGY_ID, strategy);

        assertEquals(storedStrategy, result);
    }

    @Test
    void updateStrategyNotFound() {
        when(strategyRepository.findById(STRATEGY_ID)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> strategyService.updateStrategy(STRATEGY_ID, new Strategy()));

    }

    @Test
    void deleteTrade() {
        Strategy strategy = mock(Strategy.class);
        when(strategy.getAccountId()).thenReturn(ACCOUNT_ID);

        when(strategyRepository.findById(STRATEGY_ID)).thenReturn(Optional.of(strategy));

        boolean result = strategyService.deleteStrategy(STRATEGY_ID);

        assertTrue(result);
        verify(strategyRepository).deleteById(STRATEGY_ID);
        verify(accountSyncService).syncStrategies(ACCOUNT_ID);
    }

    @Test
    void deleteTradeNotFound() {
        when(strategyRepository.findById(STRATEGY_ID)).thenReturn(Optional.empty());

        boolean result = strategyService.deleteStrategy(STRATEGY_ID);

        assertFalse(result);
        verify(strategyRepository, times(0)).deleteById(any());
        verify(accountSyncService, times(0)).syncStrategies(any());
    }
}