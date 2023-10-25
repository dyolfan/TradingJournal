package com.dyolfan.tradingjournal.data.overview;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class OverviewBase {
    private BigDecimal winRate;
    private int tradesAmount;
    private int profitTradesAmount;
    private int lossTradeAmount;
    private BigDecimal winPnl;
    private BigDecimal lossPnl;
    private BigDecimal pnl;
}
