package com.example.restfuzz_backend.core.fuzzer.type;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.Field;

public class DecimalInIntegerFieldFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Put decimal in integer field";

    private static DecimalInIntegerFieldFuzzer decimalInIntegerFieldFuzzer = new DecimalInIntegerFieldFuzzer();

    private DecimalInIntegerFieldFuzzer() {
    }

    public static DecimalInIntegerFieldFuzzer getInstance() {
        return decimalInIntegerFieldFuzzer;
    }

    /**
     * 针对 Integer Field
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String fuzzValue = StringGenerator.generateDecimalRandomCase();
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
