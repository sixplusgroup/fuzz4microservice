package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.model.field.Field;

import java.util.List;

public class EmptyValueFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "empty";

    private static EmptyValueFuzzer emptyValueFuzzer = new EmptyValueFuzzer();

    private EmptyValueFuzzer(){}

    public static EmptyValueFuzzer getInstance(){
        return emptyValueFuzzer;
    }

    /**
     * 针对各类Field
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            field.setFuzzValue("");
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
