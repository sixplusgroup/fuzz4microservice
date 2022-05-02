package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.Field;

public class RandomUnicodeStringFuzzer extends BaseFuzzer {

    public static final String DESCRIPTION = "random Unicode string";

    private static RandomUnicodeStringFuzzer randomUnicodeStringFuzzer = new RandomUnicodeStringFuzzer();

    private RandomUnicodeStringFuzzer() {}

    public static RandomUnicodeStringFuzzer getInstance() {
        return randomUnicodeStringFuzzer;
    }

    /**
     * 针对 String Field
     */
    @Override
    public Field fuzz(Field field) {
        try {
            field = (Field) field.clone();
            String randomUnicode = StringGenerator.generateRandomUnicodeCase();
            field.setFuzzValue(randomUnicode);
            field.setFuzzedBy(DESCRIPTION);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return field;
    }

}
