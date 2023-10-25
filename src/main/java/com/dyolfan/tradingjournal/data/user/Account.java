package com.dyolfan.tradingjournal.data.user;

import com.dyolfan.tradingjournal.data.trade.Currency;
import com.dyolfan.tradingjournal.data.trade.Strategies;
import com.dyolfan.tradingjournal.data.trade.Trades;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Getter
@Setter
@Document("accounts")
public class Account {
    @Id
    private String id;
    private String name;
    private Currency currency;
    @DBRef
    private Trades trades;
    @DBRef
    private Strategies strategies;
}
