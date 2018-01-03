package com.agh.tramsim.factors;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Tram;

import java.math.BigDecimal;

public class TimeFactor extends Factor {

    public TimeFactor(BigDecimal initialValue) {
        super(initialValue);
    }

    @Override
    public BigDecimal calculateValue(Tram tram) {
        return null;
    }

}
