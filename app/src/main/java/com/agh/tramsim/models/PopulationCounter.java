package com.agh.tramsim.models;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.Tram;
import com.agh.tramsim.factors.StopFactor;
import com.agh.tramsim.factors.TimeFactor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PopulationCounter {

    private BigDecimal population;
    private Tram tram;
    private List<Factor> factors;

    public PopulationCounter(Tram tram) {
        this.tram = tram;
        factors = new ArrayList<>();
        factors.add(new StopFactor(tram));
        factors.add(new TimeFactor(tram));
    }

    public BigDecimal getPopulation() {
        return population;
    }

    public void calculatePopulation() {
        population = BigDecimal.valueOf(tram.getCapacity());
        for (Factor factor : factors) {
            factor.calculateValue();
            population = population.multiply(factor.getValue());
        }
    }

}
