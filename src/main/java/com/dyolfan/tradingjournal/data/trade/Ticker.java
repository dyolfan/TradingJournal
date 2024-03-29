package com.dyolfan.tradingjournal.data.trade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Ticker {
    @NotNull
    private Currency main;
    @NotNull
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
