package com.example.restfuzz_backend.core.fuzzer.type;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.fuzzer.extreme.ExtremeNegativeNumberValuesFuzzer;
import com.example.restfuzz_backend.core.model.field.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class StringInNumberFieldFuzzerTest {
    @Test
    public void testFuzz() {
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$");

        Field integerField = FuzzerTestAssistant.getMockIntegerField();
        Field fuzzedIntegerField = StringInNumberFieldFuzzer.getInstance().fuzz(integerField);
        Assert.assertFalse(pattern.matcher(fuzzedIntegerField.getFuzzValue()).matches());

        Field decimalField = FuzzerTestAssistant.getMockDecimalField();
        Field fuzzedDecimalField = StringInNumberFieldFuzzer.getInstance().fuzz(decimalField);
        Assert.assertFalse(pattern.matcher(fuzzedDecimalField.getFuzzValue()).matches());
    }
}
