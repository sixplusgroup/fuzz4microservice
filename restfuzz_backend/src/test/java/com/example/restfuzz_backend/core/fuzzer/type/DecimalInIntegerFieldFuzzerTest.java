package com.example.restfuzz_backend.core.fuzzer.type;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class DecimalInIntegerFieldFuzzerTest {
    @Test
    public void testFuzz() {
        Pattern pattern = Pattern.compile("^(-?\\d+)\\.(\\d+)$");

        Field integerField = FuzzerTestAssistant.getMockIntegerField();
        Field fuzzedIntegerField = DecimalInIntegerFieldFuzzer.getInstance().fuzz(integerField);
        Assert.assertTrue(pattern.matcher(fuzzedIntegerField.getFuzzValue()).matches());
    }
}
