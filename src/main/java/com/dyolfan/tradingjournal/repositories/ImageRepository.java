package com.dyolfan.tradingjournal.repositories;

import com.dyolfan.tradingjournal.data.trade.TradeScreenshot;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageRepository extends MongoRepository<TradeScreenshot, String> {
}
