package com.agh.tramsim.elements;

import java.math.BigDecimal;

public class Position {

    private BigDecimal x;
    private BigDecimal y;

    public Position() {
    }

    public Position(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }
}
