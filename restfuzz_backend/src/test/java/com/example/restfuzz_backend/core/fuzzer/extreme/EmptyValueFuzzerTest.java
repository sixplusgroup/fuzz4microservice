package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.Field;
import org.junit.Assert;
import org.junit.Test;

public class EmptyValueFuzzerTest {
    @Test
    public void testFuzz() {
        Field stringField = FuzzerTestAssistant.getMockStringField();
        Field fuzzedStringField = EmptyValueFuzzer.getInstance().fuzz(stringField);
        Assert.assertEquals("", fuzzedStringField.getFuzzValue());

        Field integerField = FuzzerTestAssistant.getMockIntegerField();
        Field fuzzedIntegerField = EmptyValueFuzzer.getInstance().fuzz(integerField);
        Assert.assertEquals("", fuzzedIntegerField.getFuzzValue());

        Field decimalField = FuzzerTestAssistant.getMockDecimalField();
        Field fuzzedDecimalField = EmptyValueFuzzer.getInstance().fuzz(decimalField);
        Assert.assertEquals("", fuzzedDecimalField.getFuzzValue());

        Field booleanField = FuzzerTestAssistant.getMockBooleanField();
        Field fuzzedBooleanField = EmptyValueFuzzer.getInstance().fuzz(booleanField);
        Assert.assertEquals("", fuzzedBooleanField.getFuzzValue());
    }
}
