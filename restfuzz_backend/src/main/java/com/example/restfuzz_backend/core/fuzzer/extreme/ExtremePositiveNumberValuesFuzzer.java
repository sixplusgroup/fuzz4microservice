package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.DecimalGenerator;
import com.example.restfuzz_backend.core.generator.simple.IntegerGenerator;
import com.example.restfuzz_backend.core.model.field.DecimalField;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.model.field.IntegerField;

public class ExtremePositiveNumberValuesFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Extreme positive value";

    private static ExtremePositiveNumberValuesFuzzer extremePositiveNumberValuesFuzzer =
            new ExtremePositiveNumberValuesFuzzer();

    private ExtremePositiveNumberValuesFuzzer(){}

    public static ExtremePositiveNumberValuesFuzzer getInstance() {
        return extremePositiveNumberValuesFuzzer;
    }

    /**
     * 针对两类Field:
     * 1. integer
     * 2. decimal
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String fuzzValue = null;
            if (field instanceof IntegerField) {
                fuzzValue = String.valueOf(IntegerGenerator.MOST_POSITIVE_INTEGER);
            } else if (field instanceof DecimalField) {
                fuzzValue = String.valueOf(DecimalGenerator.MOST_POSITIVE_DECIMAL);
            }
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
