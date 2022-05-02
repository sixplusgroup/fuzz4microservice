package com.example.restfuzz_backend.core.model;

import com.example.restfuzz_backend.core.enums.HttpMethod;
import com.example.restfuzz_backend.core.fuzzer.FuzzerTestAssistant;
import com.example.restfuzz_backend.core.model.test.Case;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;

public class CaseTest {
    @Test
    public void testEquals() {
        Case case1 = new Case(
                1,
                1,
                "http://fuzz.test",
                HttpMethod.GET,
                "fuzzedFieldName",
                "fuzzer",
                Arrays.asList(FuzzerTestAssistant.getMockStringField(),
                        FuzzerTestAssistant.getMockStringWithFormatField(),
                        FuzzerTestAssistant.getMockIntegerField(),
                        FuzzerTestAssistant.getMockDecimalField(),
                        FuzzerTestAssistant.getMockBooleanField())
        );
        Case case2 = new Case(
                2,
                1,
                "http://fuzz.test",
                HttpMethod.GET,
                "fuzzedFieldName",
                "fuzzer",
                Arrays.asList(FuzzerTestAssistant.getMockStringField(),
                        FuzzerTestAssistant.getMockStringWithFormatField(),
                        FuzzerTestAssistant.getMockIntegerField(),
                        FuzzerTestAssistant.getMockDecimalField(),
                        FuzzerTestAssistant.getMockBooleanField())
        );
        Assert.assertEquals(case1, case2);
        Set<Case> caseSet = new HashSet<>();
        caseSet.add(case1);
        caseSet.add(case2);
        Assert.assertEquals(1, caseSet.size());
        List<Case> caseList = new ArrayList<>();
        caseList.add(case1);
        Assert.assertTrue(caseList.contains(case2));
    }
}
