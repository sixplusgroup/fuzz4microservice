package com.example.restfuzz_backend.core.fuzzer.extreme;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.StringField;
import org.junit.Assert;
import org.junit.Test;

public class StringFormatTotallyWrongValuesFuzzerTest {
    @Test
    public void testFuzz() {
        StringField stringWithFormatField = (StringField) FuzzerTestAssistant.getMockStringWithFormatField();
        StringField fuzzedStringWithFormatField =
                (StringField) StringFormatTotallyWrongValuesFuzzer.getInstance().fuzz(stringWithFormatField);
        Assert.assertNotNull(fuzzedStringWithFormatField);
        Assert.assertNotEquals(stringWithFormatField.getSeed(), fuzzedStringWithFormatField.getFuzzValue());
    }
}
