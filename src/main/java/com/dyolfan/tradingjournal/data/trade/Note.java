package com.dyolfan.tradingjournal.data.trade;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Note {
    private NoteType type;
    private String text;
}
