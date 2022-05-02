package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.model.field.StringField;

import java.util.List;

public class StringFormatTotallyWrongValuesFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "Totally wrong value";

    private static StringFormatTotallyWrongValuesFuzzer stringFormatTotallyWrongValuesFuzzer =
            new StringFormatTotallyWrongValuesFuzzer();

    private StringFormatTotallyWrongValuesFuzzer(){}

    public static StringFormatTotallyWrongValuesFuzzer getInstance() {
        return stringFormatTotallyWrongValuesFuzzer;
    }

    /**
     * 针对一类Field: 有format的string
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String fuzzValue = StringGenerator.generateTotallyWrongCaseForFieldWithFormat((StringField) field);
            field.setFuzzValue(fuzzValue);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
