package com.dyolfan.tradingjournal.data.trade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("strategies")
public class Strategy {
    @Id
    private String id;
    @NotNull
    private String name;
    private List<String> tags;
    private List<String> steps;
    private String notes;
    private String description;
    @NotNull
    private String accountId;
}
