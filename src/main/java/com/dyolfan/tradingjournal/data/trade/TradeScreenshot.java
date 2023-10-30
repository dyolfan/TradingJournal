package com.dyolfan.tradingjournal.data.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "tradeScreenshots")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TradeScreenshot {
    @Id
    private String id;
    private String tradeId;
    private Binary image;
}
