package com.agh.tramsim.core;

import com.agh.tramsim.elements.Tram;

import java.math.BigDecimal;

public abstract class Factor {

    protected BigDecimal value;
    protected Tram tram;

    public Factor(Tram tram) {
        this.tram = tram;
    }

    public BigDecimal getValue() {
        return value;
    }

    public abstract void calculateValue();

}
