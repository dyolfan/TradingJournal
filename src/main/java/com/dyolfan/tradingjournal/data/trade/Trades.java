package com.dyolfan.tradingjournal.data.trade;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
public class Trades extends ArrayList<Trade> {

    public Trades(Trade... trades) {
        super(List.of(trades));
    }

    public Trades(Collection<Trade> trades) {
        super(trades);
    }
}
