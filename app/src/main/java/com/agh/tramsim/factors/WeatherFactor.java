package com.agh.tramsim.factors;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Tram;

import java.math.BigDecimal;

public class WeatherFactor extends Factor {

    public WeatherFactor(BigDecimal scalar) {
        super(scalar);
    }

    @Override
    public BigDecimal calculateValue(Tram tram) {
        return null;
    }

}
