package com.dyolfan.tradingjournal.data.trade;

import java.util.List;

public class StrategyTest {
    private static final String STRATEGY_NAME = "ICT";
    private static final String STRATEGY_TAG = "London Sweep";
    private static final String STRATEGY_DESCRIPTION = "Gain profits";
    private static final String STRATEGY_NOTES = "Be precise";
    private static final List<String> STRATEGY_STEPS = List.of("Find", "Prepare", "Execute");

    public static Strategy sample() {
        Strategy sample=  new Strategy();
        sample.setName(STRATEGY_NAME);
        sample.setTags(List.of(STRATEGY_TAG));
        sample.setDescription(STRATEGY_DESCRIPTION);
        sample.setNotes(STRATEGY_NOTES);
        sample.setSteps(STRATEGY_STEPS);
        return sample;
    }
}