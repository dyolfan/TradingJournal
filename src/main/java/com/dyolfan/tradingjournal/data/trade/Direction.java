package com.dyolfan.tradingjournal.data.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
    LONG("Long", "S"),
    SHORT("Short", "L");

    private final String fullName;
    private final String shortName;
}
