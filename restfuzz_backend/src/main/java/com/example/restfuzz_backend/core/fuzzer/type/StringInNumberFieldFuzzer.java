package com.example.restfuzz_backend.core.fuzzer.type;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.Field;

import java.util.List;

public class StringInNumberFieldFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Put string in number field";

    private static StringInNumberFieldFuzzer stringInNumberFieldFuzzer = new StringInNumberFieldFuzzer();

    private StringInNumberFieldFuzzer(){}

    public static StringInNumberFieldFuzzer getInstance() {
        return stringInNumberFieldFuzzer;
    }

    /**
     * 针对 Integer、Decimal Field
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String fuzzValue = StringGenerator.generateNotNumberRandomCase();
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
