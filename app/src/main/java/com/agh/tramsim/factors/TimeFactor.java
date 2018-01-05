package com.agh.tramsim.factors;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Tram;

import java.math.BigDecimal;
import java.time.LocalTime;

public class TimeFactor extends Factor {

    public TimeFactor(Tram tram) {
        super(tram);
    }

    @Override
    public void calculateValue() {
        LocalTime localTime = LocalTime.now();
        int hour = localTime.getHour();
        if (hour < 5 || hour > 23) {
            value = BigDecimal.ZERO;
            return;
        }
        if (hour < 7) {
            value = BigDecimal.valueOf(0.2);
            return;
        }
        if (hour < 9) {
            value = BigDecimal.ONE;
            return;
        }
        if (hour < 11) {
            value = BigDecimal.valueOf(0.7);
            return;
        }
        if (hour < 13) {
            value = BigDecimal.valueOf(0.4);
            return;
        }
        if (hour < 15) {
            value = BigDecimal.valueOf(0.5);
            return;
        }
        if (hour < 16) {
            value = BigDecimal.valueOf(0.7);
            return;
        }
        if (hour < 18) {
            value = BigDecimal.ONE;
            return;
        }
        if (hour < 19) {
            value = BigDecimal.valueOf(0.85);
            return;
        }
        if (hour < 20) {
            value = BigDecimal.valueOf(0.7);
            return;
        }
        if (hour < 22) {
            value = BigDecimal.valueOf(0.4);
            return;
        }
        if (hour < 23) {
            value = BigDecimal.valueOf(0.2);
        }
    }

}
