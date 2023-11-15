package com.dyolfan.tradingjournal.data.trade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Note {
    @NotNull
    private NoteType type;
    private String text;
}
