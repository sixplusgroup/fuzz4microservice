package com.example.restfuzz_backend.core.model.field;

import com.example.restfuzz_backend.core.model.field.Field;
import lombok.Getter;

public class IntegerField extends Field {

    @Getter
    private long minValue;

    @Getter
    private long maxValue;

    public IntegerField(String fieldName, String seed) {
        super(fieldName, seed);
    }

    public void setMinValue(long minValue) {
        this.minValue = minValue;
        this.hasMinConstraint = true;
    }

    public void setMaxValue(long maxValue) {
        this.maxValue = maxValue;
        this.hasMaxConstraint = true;
    }

}
