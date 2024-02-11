package com.dyolfan.tradingjournal.data.user;

import com.dyolfan.tradingjournal.data.trade.Currency;
import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Trades;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@Document("accounts")
public class Account {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private Currency currency;
    @DBRef
    @NotNull
    private Trades trades = new Trades();
    @DBRef
    @NotNull
    private Strategies strategies = new Strategies();

    public Account() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Account accountObj)) {
            return false;
        }

        return Objects.equals(this.getId(), accountObj.getId())
                && Objects.equals(this.getName(), accountObj.getName())
                && Objects.equals(this.getCurrency(), accountObj.getCurrency())
                && Objects.equals(this.getTrades(), accountObj.getTrades())
                && Objects.equals(this.getStrategies(), accountObj.getStrategies());
    }
}
