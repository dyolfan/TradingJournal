package com.dyolfan.tradingjournal.data.trade;


import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Notes extends ArrayList<Note> {
    public Notes(Note... notes) {
        super(List.of(notes));
    }
}
