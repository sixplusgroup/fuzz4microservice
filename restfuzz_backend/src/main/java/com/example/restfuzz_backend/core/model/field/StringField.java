package com.example.restfuzz_backend.core.model.field;

import com.example.restfuzz_backend.core.enums.StringFieldFormat;
import lombok.Getter;
import lombok.Setter;

public class StringField extends Field {

    @Getter
    private long maxLength;

    @Getter
    private long minLength;

    @Getter
    @Setter
    private StringFieldFormat stringFieldFormat;

    public StringField(String fieldName, String seed) {
        super(fieldName, seed);
        this.stringFieldFormat = StringFieldFormat.NONE;
    }

    public StringField(String fieldName, String seed,  StringFieldFormat stringFieldFormat) {
        super(fieldName, seed);
        this.stringFieldFormat = stringFieldFormat;
    }

    public void setMaxLength(long maxLength) {
        this.maxLength = maxLength;
        this.hasMaxConstraint = true;
    }

    public void setMinLength(long minLength) {
        this.minLength = minLength;
        this.hasMinConstraint = true;
    }

    public boolean hasFormat() {
        return this.stringFieldFormat != StringFieldFormat.NONE;
    }
}
