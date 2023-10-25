package com.dyolfan.tradingjournal.data.trade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Trades extends ArrayList<Trade> {
    public Trades() {
        super();
    }

    public Trades(Trade... trades) {
        super(List.of(trades));
    }

    public Trades(Collection<Trade> trades) {
        super(trades);
    }
}
