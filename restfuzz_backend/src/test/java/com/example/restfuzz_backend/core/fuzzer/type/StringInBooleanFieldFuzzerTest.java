package com.example.restfuzz_backend.core.fuzzer.type;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

public class StringInBooleanFieldFuzzerTest {
    @Test
    public void testFuzz() {
        Pattern pattern = Pattern.compile("(true|True|TRUE|false|False|FALSE)");

        Field booleanField = FuzzerTestAssistant.getMockBooleanField();
        Field fuzzedBooleanField = StringInBooleanFieldFuzzer.getInstance().fuzz(booleanField);
        Assert.assertFalse(pattern.matcher(fuzzedBooleanField.getFuzzValue()).matches());
    }
}
