package com.example.restfuzz_backend.core.fuzzer;

import com.example.restfuzz_backend.core.enums.StringFieldFormat;
import com.example.restfuzz_backend.core.model.field.*;

public class FuzzerTestAssistant {

    public static Field getMockStringField() {
        StringField field = new StringField("name", "book");
        field.setMaxLength(30);
        field.setMinLength(5);
        return field;
    }

    public static Field getMockStringWithFormatField() {
        StringField field = new StringField("address", "abc@rr.com");
        field.setStringFieldFormat(StringFieldFormat.EMAIL);
        return field;
    }

    public static Field getMockIntegerField() {
        IntegerField field = new IntegerField("quantity", "124");
        field.setMaxValue(1000);
        field.setMinValue(50);
        return field;
    }

    public static Field getMockDecimalField() {
        DecimalField field = new DecimalField("price", "15.7");
        field.setMaxValue(200);
        field.setMinValue(10.5);
        return field;
    }

    public static Field getMockBooleanField() {
        return new BooleanField("isNovel", "true");
    }

}
