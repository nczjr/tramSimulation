package com.agh.tramsim.elements;

import com.agh.tramsim.models.Population;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

import static java.math.RoundingMode.DOWN;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tram {

    private int number;
    private String name;
    private String tripId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private Position currentPosition;
    private Route currentRoute;
    private TramRoute tramRoute;
    private Population population;
    private int capacity;

    public Tram() {
        this.population = new Population(this);
    }

    public Tram(Position currentPosition, TramRoute tramRoute, Population population) {
        this.currentPosition = currentPosition;
        this.tramRoute = tramRoute;
        this.population = population;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void calculateCurrentPosition() {
        BigDecimal x = latitude.divide(BigDecimal.valueOf(3600000.0), 7, DOWN);
        BigDecimal y = longitude.divide(BigDecimal.valueOf(3600000.0), 7, DOWN);
        currentPosition = new Position(x, y);
    }

    public void readNumberFromName() {
        number = Integer.parseInt(name.split(" ")[0]);
    }
}
