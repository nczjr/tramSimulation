package com.agh.tramsim.elements;

import com.agh.tramsim.models.PopulationCounter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tram {

    private int number;
    private String Name;
    private String tripId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Position currentPosition;
    private Route currentRoute;
    private TramRoute tramRoute;
    private PopulationCounter populationCounter;
    private int capacity;

    public Tram() {
        this.populationCounter = new PopulationCounter(this);
    }

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public Route getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(Route currentRoute) {
        this.currentRoute = currentRoute;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void calculateCurrentPosition() {
        BigDecimal x = longitude.divide(BigDecimal.valueOf(3600000.0), 7);
        BigDecimal y = latitude.divide(BigDecimal.valueOf(3600000.0), 7);
        currentPosition = new Position(x, y);
    }
}
