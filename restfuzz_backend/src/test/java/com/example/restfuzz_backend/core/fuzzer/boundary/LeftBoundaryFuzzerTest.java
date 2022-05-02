package com.example.restfuzz_backend.core.fuzzer.boundary;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.DecimalField;
import com.example.restfuzz_backend.core.model.field.IntegerField;
import com.example.restfuzz_backend.core.model.field.StringField;
import org.junit.Assert;
import org.junit.Test;

public class LeftBoundaryFuzzerTest {

    @Test
    public void testFuzz() {
        // 对于字符串，有min的长度小于min，没有的长度小于seed
        StringField stringField = (StringField) FuzzerTestAssistant.getMockStringField();
        StringField fuzzedStringField = (StringField) LeftBoundaryFuzzer.getInstance().fuzz(stringField);
        if (stringField.hasMinConstraint) {
            Assert.assertTrue(fuzzedStringField.getFuzzValue().length() < stringField.getMinLength());
        } else {
            Assert.assertTrue(fuzzedStringField.getFuzzValue().length() < stringField.getSeed().length());
        }

        // 对于整数，有min的小于min，没有的小于seed
        IntegerField integerField = (IntegerField) FuzzerTestAssistant.getMockIntegerField();
        IntegerField fuzzedIntegerField = (IntegerField) LeftBoundaryFuzzer.getInstance().fuzz(integerField);
        if (integerField.hasMinConstraint) {
            Assert.assertTrue(Long.parseLong(fuzzedIntegerField.getFuzzValue()) <
                    integerField.getMinValue());
        } else {
            Assert.assertTrue(Long.parseLong(fuzzedIntegerField.getFuzzValue()) <
                    Long.parseLong(integerField.getSeed()));
        }

        // 对于小数，判断方式同整数
        DecimalField decimalField = (DecimalField) FuzzerTestAssistant.getMockDecimalField();
        DecimalField fuzzedDecimalField = (DecimalField) LeftBoundaryFuzzer.getInstance().fuzz(decimalField);
        if (decimalField.hasMinConstraint) {
            Assert.assertTrue(Double.parseDouble(fuzzedDecimalField.getFuzzValue()) <
                    decimalField.getMinValue());
        } else {
            Assert.assertTrue(Double.parseDouble(fuzzedDecimalField.getFuzzValue()) <
                    Double.parseDouble(decimalField.getSeed()));
        }
    }

}
