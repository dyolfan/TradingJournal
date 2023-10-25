package com.dyolfan.tradingjournal.data.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    EUR("€"),
    USD("$"),
    JPY("¥"),
    GBP("£"),
    AUD("AU$"),
    NZD("NZ$"),
    CAD("CA$"),
    CHF("₣"),
    HUF("Ft"),
    MXN("Mex$"),
    ZAR("R"),
    CZK("Kč"),
    PLN("zł"),
    TRY("₺"),
    RON("leu"),
    CHN("CN¥"),
    SEK("kr"),
    DKK("€");

    private final String symbol;
}
