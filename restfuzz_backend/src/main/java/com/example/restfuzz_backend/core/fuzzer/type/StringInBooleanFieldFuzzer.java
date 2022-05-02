package com.example.restfuzz_backend.core.fuzzer.type;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.Field;

import java.util.List;

public class StringInBooleanFieldFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Put string in boolean field";

    private static StringInBooleanFieldFuzzer stringInBooleanFieldFuzzer = new StringInBooleanFieldFuzzer();

    private StringInBooleanFieldFuzzer() {
    }

    public static StringInBooleanFieldFuzzer getInstance() {
        return stringInBooleanFieldFuzzer;
    }

    /**
     * 针对Boolean Field
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String fuzzValue = StringGenerator.generateNotBooleanRandomCase();
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
