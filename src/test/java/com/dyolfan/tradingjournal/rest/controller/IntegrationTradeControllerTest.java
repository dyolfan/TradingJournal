package com.dyolfan.tradingjournal.rest.controller;

import com.dyolfan.tradingjournal.commons.BaseIntegrationTest;
import com.dyolfan.tradingjournal.data.trade.*;
import com.dyolfan.tradingjournal.data.user.Account;
import org.joda.time.DateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationTradeControllerTest extends BaseIntegrationTest {
    private Account testAccount;

    @BeforeEach
    public void setUp() {
        testAccount = createTestAccount();
    }

    @AfterEach
    @Override
    public void cleanUp() {
        super.cleanUp();
        mongoTemplate.remove(testAccount);
    }

    @Test
    void addTrade() {
        Trade trade = createTestTrade(testAccount.getId(), createTestStrategy(testAccount.getId()), false);

        ResponseEntity<Trade> responseEntity = restTemplate.postForEntity(serverUrl("trades/add"), trade, Trade.class);

        tradeCleanups.add(responseEntity.getBody());

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(trade, responseEntity.getBody(), "id");
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(responseEntity.getBody().getId(), Trade.class));
    }

    @Test
    void getTradeById() {
        Trade trade = createTestTrade(testAccount.getId(), createTestStrategy(testAccount.getId()), true);

        ResponseEntity<Trade> responseEntity = restTemplate.getForEntity(
                serverUrl("trades/" + trade.getId()), Trade.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(trade, responseEntity.getBody());
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(trade.getId(), Trade.class));
    }

    @Test
    void updateTrade() {
        Trade trade = createTestTrade(testAccount.getId(), createTestStrategy(testAccount.getId()), true);
        Strategy newStrategy = createTestStrategy(testAccount.getId(), false);
        newStrategy.setName("Other");
        newStrategy = mongoTemplate.save(newStrategy);
        strategiesCleanup.add(newStrategy);

        Strategy otherStrategy = new Strategy();
        otherStrategy.setId(newStrategy.getId());


        Trade newTrade = createTestTrade(testAccount.getId(), otherStrategy, false);
        newTrade.setId(trade.getId());
        newTrade.setTicker(Ticker.parse("GBP/JPY"));
        newTrade.setDate(DateTime.now().minusDays(3).toDate());
        Outcome newOutcome = trade.getOutcome();
        newOutcome.setPnl(BigDecimal.TEN);
        newTrade.setOutcome(newOutcome);
        newTrade.setDirection(Direction.SHORT);
        LotInfo newLotInfo = trade.getLotInfo();
        newLotInfo.setRiskRewardRatio(BigDecimal.valueOf(1.2));
        newTrade.setLotInfo(newLotInfo);
        newTrade.setStrategy(newStrategy);

        ResponseEntity<Trade> responseEntity = restTemplate.exchange(serverUrl("trades/" + trade.getId() + "/update"), HttpMethod.PUT, new HttpEntity<>(newTrade), Trade.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(newTrade, responseEntity.getBody());
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(newTrade.getId(), Trade.class));
    }

    @Test
    void deleteTrade() {
        Trade trade = createTestTrade(testAccount.getId(), createTestStrategy(testAccount.getId()));

        ResponseEntity<Object> responseEntity = restTemplate.exchange(serverUrl("trades/" + trade.getId()), HttpMethod.DELETE, new HttpEntity<>(trade), Object.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertTrue((Boolean) responseEntity.getBody());
        assertNull(mongoTemplate.findById(trade.getId(), Trade.class));
    }
}