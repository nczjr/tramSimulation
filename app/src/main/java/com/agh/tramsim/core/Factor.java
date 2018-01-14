package com.agh.tramsim.core;

import com.agh.tramsim.elements.Tram;

import java.math.BigDecimal;
import java.math.RoundingMode;

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

    protected void normalizeValue(BigDecimal maxValue, BigDecimal newMinValue) {
        value = value.divide((maxValue), 5, RoundingMode.CEILING)
                .multiply(BigDecimal.ONE.subtract(newMinValue))
                .add(newMinValue);
    }

}

