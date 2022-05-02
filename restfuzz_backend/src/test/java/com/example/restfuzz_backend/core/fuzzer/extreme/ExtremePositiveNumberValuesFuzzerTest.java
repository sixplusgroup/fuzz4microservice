package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.Field;
import org.junit.Assert;
import org.junit.Test;

public class ExtremePositiveNumberValuesFuzzerTest {
    @Test
    public void testFuzz() {
        Field integerField = FuzzerTestAssistant.getMockIntegerField();
        Field fuzzedIntegerField = ExtremePositiveNumberValuesFuzzer.getInstance().fuzz(integerField);
        Assert.assertTrue(Long.parseLong(fuzzedIntegerField.getFuzzValue()) > 0);
        Assert.assertTrue(Long.parseLong(fuzzedIntegerField.getFuzzValue()) >
                Long.parseLong(integerField.getSeed()));

        Field decimalField = FuzzerTestAssistant.getMockDecimalField();
        Field fuzzedDecimalField = ExtremePositiveNumberValuesFuzzer.getInstance().fuzz(decimalField);
        Assert.assertTrue(Double.parseDouble(fuzzedDecimalField.getFuzzValue()) > 0);
        Assert.assertTrue(Double.parseDouble(fuzzedDecimalField.getFuzzValue()) >
                Double.parseDouble(decimalField.getSeed()));
    }
}
