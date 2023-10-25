package com.dyolfan.tradingjournal.data.trade;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("strategies")
public class Strategy {
    @Id
    private String id;
    private String name;
    private String subtype;
    private String description;
    private String accountId;
}
