package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Trade;
import com.dyolfan.tradingjournal.data.trade.Trades;
import com.dyolfan.tradingjournal.repositories.TradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TradeSearchService {
    private final TradeRepository tradeRepository;

    public TradeSearchService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public Optional<Trade> findTradeById(String id) {
        return tradeRepository.findById(id);
    }

    public Trades findAllTradeByIds(List<String> ids) {
        return new Trades(tradeRepository.findAllById(ids));
    }

    public Trades findTradesByAccountId(String id) {
        return new Trades(tradeRepository.findAllByAccountId(id));
    }

}
