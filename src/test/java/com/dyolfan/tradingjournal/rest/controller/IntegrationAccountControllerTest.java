package com.dyolfan.tradingjournal.rest.controller;

import com.dyolfan.tradingjournal.commons.BaseIntegrationTest;
import com.dyolfan.tradingjournal.data.trade.Currency;
import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.data.trade.Trade;
import com.dyolfan.tradingjournal.data.user.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationAccountControllerTest extends BaseIntegrationTest {
    private Account testAccount;

    @AfterEach
    @Override
    public void cleanUp() {
        super.cleanUp();
        if (testAccount.getId() != null) {
            mongoTemplate.remove(testAccount);
        }
    }

    @Test
    void addAccount() {
        Account newAccount = createTestAccount("Unit test", Currency.EUR, false);

        ResponseEntity<Account> responseEntity = restTemplate.postForEntity(serverUrl("accounts/add"), newAccount, Account.class);

        testAccount = responseEntity.getBody();
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(newAccount, responseEntity.getBody(), "id");
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(responseEntity.getBody().getId(), Account.class));
    }

    @Test
    void getAccountById() {
        List<Strategy> strategies = new ArrayList<>();
        List<Trade> trades = new ArrayList<>();
        testAccount = createTestAccountWithTrades(trades, strategies);

        ResponseEntity<Account> responseEntity = restTemplate.getForEntity(
                serverUrl("accounts/" + testAccount.getId()), Account.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(testAccount, responseEntity.getBody());
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(testAccount.getId(), Account.class));
    }

    @Test
    void updateAccount() {
        List<Strategy> strategies = new ArrayList<>();
        List<Trade> trades = new ArrayList<>();
        testAccount = createTestAccountWithTrades(trades, strategies);
        testAccount.setName("New name");
        testAccount.setCurrency(Currency.JPY);

        ResponseEntity<Account> responseEntity = restTemplate.exchange(serverUrl("accounts/" + testAccount.getId() + "/update"), HttpMethod.PUT, new HttpEntity<>(testAccount), Account.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertEqualsIgnoring(testAccount, responseEntity.getBody());
        assertEqualsIgnoring(responseEntity.getBody(), mongoTemplate.findById(testAccount.getId(), Account.class));
    }

    @Test
    void deleteAccount() {
        testAccount = createTestAccount();

        ResponseEntity<Object> responseEntity = restTemplate.exchange(serverUrl("accounts/" + testAccount.getId()), HttpMethod.DELETE, new HttpEntity<>(null), Object.class);

        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        assertTrue((Boolean) responseEntity.getBody());
        assertNull(mongoTemplate.findById(testAccount.getId(), Account.class));
    }
}