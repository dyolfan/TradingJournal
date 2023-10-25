package com.dyolfan.tradingjournal.repositories;

import com.dyolfan.tradingjournal.data.trade.Trade;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRepository extends MongoRepository<Trade, String> {
    List<Trade> findAllByAccountId(String accountId);
}
