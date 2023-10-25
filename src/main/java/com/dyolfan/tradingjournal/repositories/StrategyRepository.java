package com.dyolfan.tradingjournal.repositories;

import com.dyolfan.tradingjournal.data.trade.Strategy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StrategyRepository extends MongoRepository<Strategy, String> {
    List<Strategy> findByAccountId(String accountId);
}
