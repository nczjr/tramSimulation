package com.agh.tramsim.elements;

import java.math.BigDecimal;

public class Stop {

    private final String name;

    private final BigDecimal value;

    public Stop(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

}
