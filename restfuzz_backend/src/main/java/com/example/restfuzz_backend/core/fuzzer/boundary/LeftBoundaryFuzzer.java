package com.example.restfuzz_backend.core.fuzzer.boundary;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.DecimalGenerator;
import com.example.restfuzz_backend.core.generator.simple.IntegerGenerator;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.DecimalField;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.model.field.IntegerField;
import com.example.restfuzz_backend.core.model.field.StringField;


public class LeftBoundaryFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Left boundary";

    private static LeftBoundaryFuzzer leftBoundaryFuzzer = new LeftBoundaryFuzzer();

    private LeftBoundaryFuzzer() {}

    public static LeftBoundaryFuzzer getInstance() {
        return leftBoundaryFuzzer;
    }

    /**
     * 针对三类Field:
     * 1. 无format的string
     * 2. integer
     * 3. decimal
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String fuzzValue = null;
            if (field instanceof StringField) {
                fuzzValue = StringGenerator.generateShorterCase((StringField) field);
            } else if (field instanceof IntegerField) {
                fuzzValue = IntegerGenerator.generateLeftBoundaryInteger((IntegerField) field);
            } else if (field instanceof DecimalField) {
                fuzzValue = DecimalGenerator.generateLeftBoundaryDecimal((DecimalField) field);
            }
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
