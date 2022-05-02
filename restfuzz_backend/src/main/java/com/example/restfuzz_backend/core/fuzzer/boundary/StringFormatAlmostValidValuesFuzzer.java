package com.example.restfuzz_backend.core.fuzzer.boundary;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.model.field.StringField;


public class StringFormatAlmostValidValuesFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Almost valid value";

    private static StringFormatAlmostValidValuesFuzzer stringFormatAlmostValidValuesFuzzer =
            new StringFormatAlmostValidValuesFuzzer();

    private StringFormatAlmostValidValuesFuzzer(){}

    public static StringFormatAlmostValidValuesFuzzer getInstance() {
        return stringFormatAlmostValidValuesFuzzer;
    }

    /**
     * 针对一类Field: 有format的string
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String fuzzValue = StringGenerator.generateAlmostValidCaseForFieldWithFormat((StringField) field);
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
