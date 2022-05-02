package com.example.restfuzz_backend.core.fuzzer.boundary;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.StringField;
import org.junit.Assert;
import org.junit.Test;

public class StringFormatAlmostValidValuesFuzzerTest {
    @Test
    public void testFuzz() {
        StringField stringWithFormatField = (StringField) FuzzerTestAssistant.getMockStringWithFormatField();
        StringField fuzzedStringWithFormatField =
                (StringField) StringFormatAlmostValidValuesFuzzer.getInstance().fuzz(stringWithFormatField);
        Assert.assertNotNull(fuzzedStringWithFormatField);
        Assert.assertNotEquals(stringWithFormatField.getSeed(), fuzzedStringWithFormatField.getFuzzValue());
    }
}
