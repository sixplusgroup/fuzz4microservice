package com.example.restfuzz_backend.core.model.field;

import lombok.Getter;

public class DecimalField extends Field {

    @Getter
    private double minValue;

    @Getter
    private double maxValue;

    public DecimalField(String fieldName, String seed) {
        super(fieldName, seed);
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
        this.hasMinConstraint = true;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
        this.hasMaxConstraint = true;
    }

}
