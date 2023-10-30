package com.dyolfan.tradingjournal.services.db;

import com.dyolfan.tradingjournal.data.trade.Strategy;
import com.dyolfan.tradingjournal.data.trade.Trade;
import com.dyolfan.tradingjournal.data.trade.Trades;
import com.dyolfan.tradingjournal.repositories.TradeRepository;
import com.dyolfan.tradingjournal.services.sync.AccountSyncService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TradeService {
    private final TradeRepository tradeRepository;
    private final TradeSearchService tradeSearchService;
    private final StrategyService strategiesService;
    private final AccountSyncService accountSyncService;


    public TradeService(TradeRepository tradeRepository, TradeSearchService tradeSearchService, StrategyService strategiesService, AccountSyncService accountSyncService) {
        this.tradeRepository = tradeRepository;
        this.tradeSearchService = tradeSearchService;
        this.strategiesService = strategiesService;
        this.accountSyncService = accountSyncService;
    }

    public Trade saveTrade(Trade trade) {
        Trade storedTrade = tradeRepository.save(trade);

        Optional.of(trade.getStrategy()).map(Strategy::getId).map(strategiesService::getStrategyById).ifPresent(storedTrade::setStrategy);

        accountSyncService.syncNewTrades(trade.getAccountId(), new Trades(trade));

        return storedTrade;
    }

    public Trade getTradeById(String id) {
        return tradeSearchService.findTradeById(id).orElseThrow();
    }

    public Trade updateTrade(String id, Trade trade) {
        return tradeRepository.findById(id).map(storedTrade -> {
            storedTrade.setDate(trade.getDate());
            storedTrade.setTicker(trade.getTicker());
            storedTrade.setStrategy(trade.getStrategy());
            storedTrade.setDirection(trade.getDirection());
            storedTrade.setOutcome(trade.getOutcome());
            storedTrade.setLotInfo(trade.getLotInfo());
            return storedTrade;
        }).map(tradeRepository::save).map(storedTrade -> {
            Optional.ofNullable(storedTrade.getStrategy())
                    .map(Strategy::getId)
                    .map(strategiesService::getStrategyById)
                    .ifPresent(storedTrade::setStrategy);
            return storedTrade;
        }).orElseThrow();
    }

    public boolean deleteTrade(String id) {
        return deleteTrade(id, true);
    }

    public boolean deleteTrade(String id, boolean syncAccount) {
        boolean isDeleted = false;

        try {
            Trade trade = tradeRepository.findById(id).orElseThrow();
            tradeRepository.deleteById(id);
            isDeleted = true;
            if (syncAccount) {
                accountSyncService.syncTrades(trade.getAccountId());
            }
        } catch (Exception exception) {
            // TODO: add logging
        }
        return isDeleted;
    }
}
