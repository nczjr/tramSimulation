package com.agh.tramsim.factors;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Tram;
import com.agh.tramsim.utils.JSONReader;

import java.math.BigDecimal;
import java.util.Map;

public class LineFactor extends Factor {

    private static final Map<Integer, BigDecimal> lineFactorValues = JSONReader.initLineFactorJSON();
    private static final BigDecimal MAX_LINE_FACTOR = BigDecimal.valueOf(161);

    public LineFactor(Tram tram) {
        super(tram);
    }

    @Override
    public void calculateValue() {
        value = lineFactorValues.get(tram.getNumber());
        if (value == null) {
            value = BigDecimal.ZERO;
        } else {
            normalizeValue(MAX_LINE_FACTOR, BigDecimal.valueOf(0.7));
        }
    }

}
