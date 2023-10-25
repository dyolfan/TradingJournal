package com.dyolfan.tradingjournal.data.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

@Getter
public class Outcome {
    private Boolean isProfit;
    private BigDecimal pnl;
    private TradeStatus status;
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Setter
    private TradeScreenshot screenshot;
    private Notes notes;

    public Outcome(boolean isProfit, BigDecimal pnl, TradeStatus status, Notes notes) {
        this.isProfit = isProfit;
        this.pnl = pnl;
        this.status = status;
        this.notes = notes;
    }

    public Outcome() {
    }
}
