package com.dyolfan.tradingjournal.data.trade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("trades")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Trade {
    @Id
    private String id;
    @org.springframework.lang.NonNull
    private Ticker ticker;
    @NotNull
    private Date date;
    @NotNull
    private Direction direction;
    @NotNull
    private LotInfo lotInfo;
    @DBRef
    private Strategy strategy;
    private Outcome outcome;
    @NotNull
    private String accountId;
}
