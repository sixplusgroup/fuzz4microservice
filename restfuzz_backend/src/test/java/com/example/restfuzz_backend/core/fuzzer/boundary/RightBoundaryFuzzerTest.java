package com.example.restfuzz_backend.core.fuzzer.boundary;

import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.field.DecimalField;
import com.example.restfuzz_backend.core.model.field.IntegerField;
import com.example.restfuzz_backend.core.model.field.StringField;
import org.junit.Assert;
import org.junit.Test;

public class RightBoundaryFuzzerTest {

    @Test
    public void testFuzz() {
        // 对于字符串，有max的长度大于max，没有的长度大于seed
        StringField stringField = (StringField) FuzzerTestAssistant.getMockStringField();
        StringField fuzzedStringField = (StringField) RightBoundaryFuzzer.getInstance().fuzz(stringField);
        if (stringField.hasMaxConstraint) {
            Assert.assertTrue(fuzzedStringField.getFuzzValue().length() > stringField.getMaxLength());
        } else {
            Assert.assertTrue(fuzzedStringField.getFuzzValue().length() > stringField.getSeed().length());
        }

        // 对于整数，有max的大于max，没有的大于seed
        IntegerField integerField = (IntegerField) FuzzerTestAssistant.getMockIntegerField();
        IntegerField fuzzedIntegerField = (IntegerField) RightBoundaryFuzzer.getInstance().fuzz(integerField);
        if (integerField.hasMaxConstraint) {
            Assert.assertTrue(Long.parseLong(fuzzedIntegerField.getFuzzValue()) >
                    integerField.getMaxValue());
        } else {
            Assert.assertTrue(Long.parseLong(fuzzedIntegerField.getFuzzValue()) >
                    Long.parseLong(integerField.getSeed()));
        }

        // 对于小数，判断方式同整数
        DecimalField decimalField = (DecimalField) FuzzerTestAssistant.getMockDecimalField();
        DecimalField fuzzedDecimalField = (DecimalField) RightBoundaryFuzzer.getInstance().fuzz(decimalField);
        if (decimalField.hasMaxConstraint) {
            Assert.assertTrue(Double.parseDouble(fuzzedDecimalField.getFuzzValue()) >
                    decimalField.getMaxValue());
        } else {
            Assert.assertTrue(Double.parseDouble(fuzzedDecimalField.getFuzzValue()) >
                    Double.parseDouble(decimalField.getSeed()));
        }
    }

}
