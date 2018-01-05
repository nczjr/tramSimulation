package com.agh.tramsim.elements;

import java.math.BigDecimal;

public class Stop {

    private String name;
    private int sequentialNumber;
    private BigDecimal value;

    public Stop(String name) {
        this.name = name;
    }

    public Stop(String name, int sequentialNumber) {
        this.name = name;
        this.sequentialNumber = sequentialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequentialNumber() {
        return sequentialNumber;
    }

    public void setSequentialNumber(int sequentialNumber) {
        this.sequentialNumber = sequentialNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
