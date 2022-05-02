package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.Field;
import org.junit.Assert;
import org.junit.Test;

public class RandomUnicodeStringFuzzerTest {
    @Test
    public void testFuzz() {
        Field stringField = FuzzerTestAssistant.getMockStringField();
        Field fuzzedStringField = RandomUnicodeStringFuzzer.getInstance().fuzz(stringField);
        System.out.println(fuzzedStringField.getFuzzValue());
        Assert.assertNotNull(fuzzedStringField.getFuzzValue());
    }
}
