package com.dyolfan.tradingjournal.data.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("trades")
@AllArgsConstructor
@Getter
@Setter
public class Trade {
    @Id
    private String id;
    private Ticker ticker;
    private Date date;
    private Direction direction;
    private LotInfo lotInfo;
    @DBRef
    private Strategy strategy;
    private Outcome outcome;
    private String accountId;
}
