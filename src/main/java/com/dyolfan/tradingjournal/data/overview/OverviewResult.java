package com.dyolfan.tradingjournal.data.overview;

import jakarta.validation.constraints.NotNull;

public class OverviewResult {
    @NotNull
    private OverviewBase totals;
    @NotNull
    private OverviewEntries entries;
}
