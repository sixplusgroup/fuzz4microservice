package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.DecimalGenerator;
import com.example.restfuzz_backend.core.generator.simple.IntegerGenerator;
import com.example.restfuzz_backend.core.model.field.DecimalField;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.model.field.IntegerField;

import java.util.List;

public class ExtremeNegativeNumberValuesFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Extreme negative value";

    private static ExtremeNegativeNumberValuesFuzzer extremeNegativeNumberValuesFuzzer =
            new ExtremeNegativeNumberValuesFuzzer();

    private ExtremeNegativeNumberValuesFuzzer(){}

    public static ExtremeNegativeNumberValuesFuzzer getInstance() {
        return extremeNegativeNumberValuesFuzzer;
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
                fuzzValue = String.valueOf(IntegerGenerator.MOST_NEGATIVE_INTEGER);
            } else if (field instanceof DecimalField) {
                fuzzValue = String.valueOf(DecimalGenerator.MOST_NEGATIVE_DECIMAL);
            }
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
