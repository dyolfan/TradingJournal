package com.dyolfan.tradingjournal.data.overview;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class OverviewBase {
    @NotNull
    private BigDecimal winRate;
    @NotNull
    private int tradesAmount;
    @NotNull
    private int profitTradesAmount;
    @NotNull
    private int lossTradeAmount;
    @NotNull
    private BigDecimal winPnl;
    @NotNull
    private BigDecimal lossPnl;
    @NotNull
    private BigDecimal pnl;

    public OverviewBase() {

    }
}
