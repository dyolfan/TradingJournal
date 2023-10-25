package com.dyolfan.tradingjournal.data.trade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Strategies extends ArrayList<Strategy> {
    public Strategies() {
        super();
    }

    public Strategies(Strategy... strategies) {
        super(List.of(strategies));
    }

    public Strategies(Collection<Strategy> strategies) {
        super(strategies);
    }

    public List<String> getIds() {
        return this.stream().map(Strategy::getId).collect(Collectors.toList());
    }
}
