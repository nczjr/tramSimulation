package com.agh.tramsim.elements;

import java.util.ArrayList;
import java.util.List;

public class Route {
    public String id;
    public List<Position> coordinates = new ArrayList<>();

    public Route(List<Position> coordinates) {
        this.coordinates = coordinates;
    }
}
