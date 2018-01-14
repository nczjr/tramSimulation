package com.agh.tramsim.factors;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Stop;
import com.agh.tramsim.elements.Tram;
import com.agh.tramsim.utils.TTSSParser;

import java.math.BigDecimal;
import java.util.List;

public class StopFactor extends Factor {

    private int numberOfStops;
    private Stop currentStop;

    public StopFactor(Tram tram) {
        super(tram);
    }

    @Override
    public void calculateValue() {
        updateStops();
        if (currentStop == null) {
            value = BigDecimal.ZERO;
        } else {
            double sequentialNumber = (double) currentStop.getSequentialNumber();
            if (sequentialNumber < (((double) numberOfStops) * 0.1)) {
                value = BigDecimal.valueOf(0.15);
            } else if (sequentialNumber < (((double) numberOfStops) * 0.2)) {
                value = BigDecimal.valueOf(0.25);
            } else if (sequentialNumber < (((double) numberOfStops) * 0.3)) {
                value = BigDecimal.valueOf(0.50);
            } else if (sequentialNumber < (((double) numberOfStops) * 0.4)) {
                value = BigDecimal.valueOf(0.75);
            } else if (sequentialNumber < (((double) numberOfStops) * 0.6)) {
                value = BigDecimal.ONE;
            } else if (sequentialNumber < (((double) numberOfStops) * 0.7)) {
                value = BigDecimal.valueOf(0.75);
            } else if (sequentialNumber < (((double) numberOfStops) * 0.8)) {
                value = BigDecimal.valueOf(0.45);
            } else if (sequentialNumber < (((double) numberOfStops) * 0.9)) {
                value = BigDecimal.valueOf(0.25);
            } else if (sequentialNumber < (((double) numberOfStops))) {
                value = BigDecimal.valueOf(0.1);
            } else if (sequentialNumber == ((double) numberOfStops)) {
                value = BigDecimal.ZERO;
            }
            normalizeValue(BigDecimal.ONE, BigDecimal.valueOf(0.5));
        }
    }


    private void updateStops() {
        List<Stop> stops = TTSSParser.getCurrentRouteForTram(tram.getTripId());
        if (!stops.isEmpty()){
            numberOfStops = stops.get(stops.size() - 1).getSequentialNumber();
            currentStop = stops.get(0);
        }
    }

}
