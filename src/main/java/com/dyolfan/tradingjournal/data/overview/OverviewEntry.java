package com.dyolfan.tradingjournal.data.overview;

import com.dyolfan.tradingjournal.data.trade.Trades;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OverviewEntry extends OverviewBase {
    private final String timeframeValue;
    private final Trades trades;

    public OverviewEntry(BigDecimal winRate, int tradesAmount, int profitTradesAmount, int lossTradeAmount, BigDecimal winPnl,
                         BigDecimal lossPnl, BigDecimal pnl, String timeframeValue, Trades trades) {
        super(winRate, tradesAmount, profitTradesAmount, lossTradeAmount, winPnl, lossPnl, pnl);
        this.timeframeValue = timeframeValue;
        this.trades = trades;
    }
}
