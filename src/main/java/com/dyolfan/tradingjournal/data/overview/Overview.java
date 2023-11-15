package com.dyolfan.tradingjournal.data.overview;

import com.dyolfan.tradingjournal.data.trade.Strategies;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Overview {
    @NotNull
    private OverviewTimeframe timeframe;
    private Strategies strategies;
    private OverviewResults results;
}
