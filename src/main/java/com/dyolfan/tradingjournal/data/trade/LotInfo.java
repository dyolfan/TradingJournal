package com.dyolfan.tradingjournal.data.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class LotInfo {
    private BigDecimal risk;
    private BigDecimal riskRewardRatio;
}
