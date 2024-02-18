package com.dyolfan.tradingjournal.rest.controller;

import com.dyolfan.tradingjournal.commons.BaseIntegrationTest;
import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.data.user.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationStrategyControllerTest extends BaseIntegrationTest {
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
    void addStrategy() {
        Strategy strategy = createTestStrategy(testAccount.getId(), false);

        ResponseEntity<Strategy> responseEntity = restTemplate.postForEntity(
                serverUrl("strategies/add"), strategy, Strategy.class);

        strategiesCleanup.add(responseEntity.getBody());

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(strategy, responseEntity.getBody(), "id");
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(responseEntity.getBody().getId(), Strategy.class));
    }

    @Test
    void getStrategy() {
        Strategy savedStrategy = createTestStrategy(testAccount.getId());


        ResponseEntity<Strategy> responseEntity = restTemplate.getForEntity(
                serverUrl("strategies/" + savedStrategy.getId()), Strategy.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(savedStrategy, responseEntity.getBody());
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(savedStrategy.getId(), Strategy.class));
    }

    @Test
    void updateStrategy() {
        Strategy savedStrategy = createTestStrategy(testAccount.getId());

        Strategy newStrategy = new Strategy();
        newStrategy.setId(savedStrategy.getId());
        newStrategy.setName("Test strat 2");
        newStrategy.setTags(List.of("test 2"));
        newStrategy.setDescription("test test 2");
        newStrategy.setAccountId(testAccount.getId());

        ResponseEntity<Strategy> responseEntity = restTemplate.exchange(serverUrl("strategies/" + savedStrategy.getId() + "/update"), HttpMethod.PUT, new HttpEntity<>(newStrategy), Strategy.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(newStrategy, responseEntity.getBody());
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(savedStrategy.getId(), Strategy.class));
    }

    @Test
    void deleteStrategy() {
        Strategy strategy = createTestStrategy(testAccount.getId());

        ResponseEntity<Object> responseEntity = restTemplate.exchange(serverUrl("strategies/" + strategy.getId()), HttpMethod.DELETE, new HttpEntity<>(strategy), Object.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertTrue((Boolean) responseEntity.getBody());
        assertNull(mongoTemplate.findById(strategy.getId(), Strategy.class));
    }
}