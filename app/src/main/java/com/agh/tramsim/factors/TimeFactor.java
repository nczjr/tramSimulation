package com.agh.tramsim.factors;


import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Tram;

import java.math.BigDecimal;
import java.util.Calendar;

public class TimeFactor extends Factor {

    public TimeFactor(Tram tram) {
        super(tram);
    }

    @Override
    public void calculateValue() {
        Calendar currentTime = Calendar.getInstance();
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        if (hour < 5 || hour > 23) {
            value = BigDecimal.valueOf(0.1);
        } else if (hour < 7) {
            value = BigDecimal.valueOf(0.2);
        } else if (hour < 9) {
            value = BigDecimal.ONE;
        } else if (hour < 11) {
            value = BigDecimal.valueOf(0.7);
        } else if (hour < 13) {
            value = BigDecimal.valueOf(0.4);
        } else if (hour < 15) {
            value = BigDecimal.valueOf(0.5);
        } else if (hour < 16) {
            value = BigDecimal.valueOf(0.7);
        } else if (hour < 18) {
            value = BigDecimal.ONE;
        } else if (hour < 19) {
            value = BigDecimal.valueOf(0.85);
        } else if (hour < 20) {
            value = BigDecimal.valueOf(0.7);
        } else if (hour < 22) {
            value = BigDecimal.valueOf(0.4);
        } else if (hour < 23) {
            value = BigDecimal.valueOf(0.2);
        } else {
            value = BigDecimal.valueOf(0.1);
        }
        normalizeValue(BigDecimal.ONE, BigDecimal.valueOf(0.3));
    }

}
