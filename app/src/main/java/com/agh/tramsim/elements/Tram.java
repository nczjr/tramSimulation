package com.agh.tramsim.elements;

import com.agh.tramsim.models.PopulationCounter;

public class Tram {

    private int number;

    private Position currentPosition;

    private TramRoute tramRoute;

    private PopulationCounter populationCounter;

    public Tram(Position currentPosition, TramRoute tramRoute, PopulationCounter populationCounter) {
        this.currentPosition = currentPosition;
        this.tramRoute = tramRoute;
        this.populationCounter = populationCounter;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public TramRoute getTramRoute() {
        return tramRoute;
    }

    public void setTramRoute(TramRoute tramRoute) {
        this.tramRoute = tramRoute;
    }

    public PopulationCounter getPopulationCounter() {
        return populationCounter;
    }

    public void setPopulationCounter(PopulationCounter populationCounter) {
        this.populationCounter = populationCounter;
    }
}
