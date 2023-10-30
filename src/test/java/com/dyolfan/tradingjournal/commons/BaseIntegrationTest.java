package com.dyolfan.tradingjournal.commons;

import com.dyolfan.tradingjournal.config.IntegrationTestConfig;
import com.dyolfan.tradingjournal.data.trade.*;
import com.dyolfan.tradingjournal.data.user.Account;
import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Import(IntegrationTestConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {
    @Autowired
    protected MongoTemplate mongoTemplate;
    @Autowired
    protected TestRestTemplate restTemplate;
    @LocalServerPort
    protected int port;

    protected final List<Strategy> strategiesCleanup = new ArrayList<>();
    protected final List<Trade> tradeCleanups = new ArrayList<>();

    @AfterEach
    public void cleanUp() {
        strategiesCleanup.forEach(mongoTemplate::remove);
        tradeCleanups.forEach(mongoTemplate::remove);
    }

    protected String serverUrl(String path) {
        return "http://localhost:" + port + "/" + path;
    }


    protected Account createTestAccount() {
        return createTestAccount("Test", Currency.USD);
    }

    protected Account createTestAccount(String name, Currency currency) {
        return createTestAccount(name, currency, true);
    }

    protected Account createTestAccount(String name, Currency currency, boolean store) {
        Account account = new Account();
        account.setName(name);
        account.setCurrency(currency);
        if (store) {
            return mongoTemplate.save(account);
        }
        return account;
    }

    protected Account createTestAccountWithTrades(List<Trade> trades, List<Strategy> strategies) {
        Account account = createTestAccount();
        Strategy strategy = createTestStrategy(account.getId());
        Strategy strategy2 = createTestStrategy("test 2", "Test 2", "Win more!", account.getId(), true);

        Trade trade = createTestTrade(account.getId(), strategy);

        Trade trade2 = createTestTrade(account.getId(), strategy, false);
        trade2.setTicker(Ticker.parse("AUD/CAD"));
        trade2.setDate(new DateTime().minusDays(2).toDate());
        trade2 = storeTrade(trade2);

        Trade trade3 = createTestTrade(account.getId(), strategy2, false);
        trade3.setDate(new DateTime().minusDays(4).toDate());
        trade3.setTicker(Ticker.parse("GBP/JPY"));
        trade3 = storeTrade(trade3);

        strategies.add(strategy);
        strategies.add(strategy2);

        trades.add(trade);
        trades.add(trade2);
        trades.add(trade3);
        storeTradeToAccount(account.getId(), trade2);
        storeTradeToAccount(account.getId(), trade3);

        account.getStrategies().addAll(strategies);
        account.getTrades().addAll(trades);

        return account;
    }

    protected Strategy createTestStrategy(String accountId) {
        return createTestStrategy(accountId, true);
    }

    protected Strategy createTestStrategy(String accountId, boolean store) {
        return createTestStrategy("Test strat", "test", "test test.", accountId, store);
    }

    protected Strategy createTestStrategy(String name, String subtype, String description, String accountId, boolean store) {
        Strategy strategy = new Strategy();
        strategy.setName(name);
        strategy.setSubtype(subtype);
        strategy.setDescription(description);
        strategy.setAccountId(accountId);

        if (store) {
            strategy = storeStrategy(strategy);
            storeStrategyToAccount(accountId, strategy);
            return strategy;
        }


        return strategy;
    }

    protected Trade createTestTrade(String accountId, Strategy strategy) {
        return createTestTrade(accountId, strategy, true);
    }

    protected Trade createTestTrade(String accountId, Strategy strategy, boolean store) {
        return createTestTrade(accountId,
                strategy,
                Ticker.parse("EUR/USD"),
                new Date(), Direction.LONG,
                new LotInfo(BigDecimal.valueOf(20), BigDecimal.valueOf(2.5)),
                new Outcome(true, BigDecimal.valueOf(50), TradeStatus.CLOSED, new Notes(new Note(NoteType.OBSERVATION, "Good!"))),
                store);
    }

    protected Trade createTestTrade(String accountId, Strategy strategy, Ticker ticker, Date date, Direction direction, LotInfo lotInfo, Outcome outcome, boolean store) {
        Trade trade = new Trade();
        trade.setAccountId(accountId);
        trade.setStrategy(strategy);
        trade.setTicker(ticker);
        trade.setDate(date);
        trade.setDirection(direction);
        trade.setLotInfo(lotInfo);
        trade.setOutcome(outcome);

        if (store) {
            trade = storeTrade(trade);
            storeTradeToAccount(accountId, trade);
            return trade;
        }


        return trade;
    }

    protected <T> void assertEqualsIgnoring(T expected, T value, String... ignoredFields) {
        assertThat(expected).usingRecursiveComparison().ignoringFields(ignoredFields).isEqualTo(value);
    }

    private Trade storeTrade(Trade trade) {
        tradeCleanups.add(trade);
        return mongoTemplate.save(trade);
    }

    private Strategy storeStrategy(Strategy strategy) {
        strategiesCleanup.add(strategy);
        return mongoTemplate.save(strategy);
    }

    private Account storeTradeToAccount(String accountId, Trade trade) {
        Account account = mongoTemplate.findById(accountId, Account.class);
        if (!account.getTrades().contains(trade)) {
            account.getTrades().add(trade);
        }
        return mongoTemplate.save(account);
    }

    private Account storeStrategyToAccount(String accountId, Strategy strategy) {
        Account account = mongoTemplate.findById(accountId, Account.class);

        if (!account.getStrategies().contains(strategy)) {
            account.getStrategies().add(strategy);
        }

        return mongoTemplate.save(account);
    }
}
