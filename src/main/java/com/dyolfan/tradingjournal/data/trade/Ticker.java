package com.dyolfan.tradingjournal.data.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Ticker {
    private Currency main;
    private Currency secondary;

    @Override
    public String toString() {
        return main.name() + "/" + secondary.name();
    }

    public static Ticker parse(String tickerValues) {
        String[] split = tickerValues.split("/");
        return new Ticker(Currency.valueOf(split[0]), Currency.valueOf(split[1]));
    }
}
