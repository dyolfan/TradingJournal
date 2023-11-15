package com.dyolfan.tradingjournal.data.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Outcome {
    @NotNull
    private Boolean isProfit;
    @NotNull
    private BigDecimal pnl;
    @NotNull
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
}
