package com.example.restfuzz_backend.core.fuzzer;

import com.example.restfuzz_backend.core.fuzzer.boundary.LeftBoundaryFuzzer;
import com.example.restfuzz_backend.core.fuzzer.boundary.RightBoundaryFuzzer;
import com.example.restfuzz_backend.core.fuzzer.boundary.StringFormatAlmostValidValuesFuzzer;
import com.example.restfuzz_backend.core.fuzzer.extreme.*;
import com.example.restfuzz_backend.core.fuzzer.type.DecimalInIntegerFieldFuzzer;
import com.example.restfuzz_backend.core.fuzzer.type.StringInBooleanFieldFuzzer;
import com.example.restfuzz_backend.core.fuzzer.type.StringInNumberFieldFuzzer;
import com.example.restfuzz_backend.core.model.field.Field;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFuzzer {

    public abstract Field fuzz(Field field);

    public List<Field> fuzzKTimes(Field field, int k) {
        List<Field> fuzzedFieldList = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            fuzzedFieldList.add(fuzz(field));
        }
        return fuzzedFieldList;
    }

    public static BaseFuzzer getFuzzerByDescription(String description) {
        if (description.equals(LeftBoundaryFuzzer.DESCRIPTION)) {
            return LeftBoundaryFuzzer.getInstance();
        }
        if (description.equals(RightBoundaryFuzzer.DESCRIPTION)) {
            return RightBoundaryFuzzer.getInstance();
        }
        if (description.equals(StringFormatAlmostValidValuesFuzzer.DESCRIPTION)) {
            return StringFormatAlmostValidValuesFuzzer.getInstance();
        }

        if (description.equals(EmptyValueFuzzer.DESCRIPTION)) {
            return EmptyValueFuzzer.getInstance();
        }
        if (description.equals(ExtremeNegativeNumberValuesFuzzer.DESCRIPTION)) {
            return ExtremeNegativeNumberValuesFuzzer.getInstance();
        }
        if (description.equals(ExtremePositiveNumberValuesFuzzer.DESCRIPTION)) {
            return ExtremePositiveNumberValuesFuzzer.getInstance();
        }
        if (description.equals(RandomUnicodeStringFuzzer.DESCRIPTION)) {
            return RandomUnicodeStringFuzzer.getInstance();
        }
        if (description.equals(StringFormatTotallyWrongValuesFuzzer.DESCRIPTION)) {
            return StringFormatTotallyWrongValuesFuzzer.getInstance();
        }

        if (description.equals(DecimalInIntegerFieldFuzzer.DESCRIPTION)) {
            return DecimalInIntegerFieldFuzzer.getInstance();
        }
        if (description.equals(StringInBooleanFieldFuzzer.DESCRIPTION)) {
            return StringInBooleanFieldFuzzer.getInstance();
        }
        if (description.equals(StringInNumberFieldFuzzer.DESCRIPTION)) {
            return StringInNumberFieldFuzzer.getInstance();
        }
        return null;
    }

}
