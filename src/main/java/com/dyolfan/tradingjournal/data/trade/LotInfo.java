package com.dyolfan.tradingjournal.data.trade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LotInfo {
    @NotNull
    private BigDecimal risk;
    @NotNull
    private BigDecimal riskRewardRatio;
}
