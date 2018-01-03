package com.agh.tramsim.models;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.factors.LineFactor;
import com.agh.tramsim.factors.StopFactor;
import com.agh.tramsim.factors.TimeFactor;
import com.agh.tramsim.factors.WeatherFactor;

import java.math.BigDecimal;

public class PopulationCounter {

    private BigDecimal population;

    Factor[] factors;

    public PopulationCounter(BigDecimal population) {
        this.population = population;
        factors = new Factor[4];
        factors[0] = new LineFactor(population);
        factors[1] = new StopFactor(population);
        factors[2] = new TimeFactor(population);
        factors[3] = new WeatherFactor(population);

    }

    public void updatePopulation() {
        BigDecimal accumulator = BigDecimal.ZERO;
        accumulator.add(population);
        for (int i = 0; i < factors.length; i++) {
            accumulator.add(factors[i].calculatePopulation());
        }
        population = accumulator;
        updateAllFactorWeights();
    }

    private void updateAllFactorWeights() {
        for (int i = 0; i < factors.length; i++) {
            factors[i].updateWeight(population);
        }
    }

}
