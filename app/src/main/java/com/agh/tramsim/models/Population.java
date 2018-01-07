package com.agh.tramsim.models;

import com.agh.tramsim.core.Factor;
import com.agh.tramsim.elements.PopulationLevel;
import com.agh.tramsim.elements.Tram;
import com.agh.tramsim.factors.StopFactor;
import com.agh.tramsim.factors.TimeFactor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Population {

    private BigDecimal population;
    private Tram tram;
    private List<Factor> factors;
    private PopulationLevel populationLevel;

    public Population(Tram tram) {
        this.tram = tram;
        factors = new ArrayList<>();
        factors.add(new StopFactor(tram));
        factors.add(new TimeFactor(tram));
    }

    public BigDecimal getPopulation() {
        return population;
    }

    public PopulationLevel getPopulationLevel() { return populationLevel; }

    public void calculatePopulation() {
        population = BigDecimal.ONE;
        for (Factor factor : factors) {
            factor.calculateValue();
            if (factor.getValue() != null) {
                population = population.multiply(factor.getValue());
            }
        }
        calculatePopulationLevel();
    }

    private void calculatePopulationLevel() {
        if (population.compareTo(BigDecimal.valueOf(0.25)) <= 0) {
            populationLevel = PopulationLevel.GREEN;
        } else if (population.compareTo(BigDecimal.valueOf(0.5)) <= 0) {
            populationLevel = PopulationLevel.YELLOW;
        } else if (population.compareTo(BigDecimal.valueOf(0.75)) <= 0) {
            populationLevel = PopulationLevel.ORANGE;
        } else if (population.compareTo(BigDecimal.ONE) <= 0) {
            populationLevel = PopulationLevel.RED;
        }
    }

}
