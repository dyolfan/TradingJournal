package com.dyolfan.tradingjournal.data.overview;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Overview {
    private OverviewTimeframe timeframe;
    private Strategies strategies;
    private OverviewResults results;
}
