package com.example.restfuzz_backend.core.model.field;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Field implements Cloneable {

    @Getter
    protected String fieldName;

    @Getter
    @Setter
    protected String seed;

    @Getter
    @Setter
    protected String fuzzValue;

    @Getter
    @Setter
    protected String fuzzedBy;

    public boolean hasMaxConstraint;
    public boolean hasMinConstraint;

    public Field(String fieldName, String seed) {
        this.fieldName = fieldName;
        this.seed = seed;
        this.fuzzValue = seed;
        this.fuzzedBy = "not fuzzed yet";
    }

    public String getAsBodyParam() {
        return "\"" + fieldName + "\": " + "\"" + fuzzValue + "\"";
    }

    public String getAsPathParam() {
        return fieldName + "=" + fuzzValue;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(getFieldName(), field.getFieldName()) &&
                Objects.equals(getSeed(), field.getSeed()) &&
                Objects.equals(getFuzzValue(), field.getFuzzValue()) &&
                Objects.equals(getFuzzedBy(), field.getFuzzedBy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldName(), getSeed(), getFuzzValue(), getFuzzedBy());
    }
}
