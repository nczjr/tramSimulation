package com.agh.tramsim.factors;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Tram;

import java.math.BigDecimal;

public class LineFactor extends Factor {

    public LineFactor(BigDecimal initialValue) {
        super(initialValue);
    }

    @Override
    public BigDecimal calculateValue(Tram tram) {
        return null;
    }

}
