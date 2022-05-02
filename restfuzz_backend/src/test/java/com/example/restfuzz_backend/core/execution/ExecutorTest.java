package com.example.restfuzz_backend.core.execution;

import com.example.restfuzz_backend.core.enums.HttpMethod;
import com.example.restfuzz_backend.core.model.test.Case;
import com.example.restfuzz_backend.core.model.test.Result;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class ExecutorTest {

    private Executor executor;

    @Before
    public void setUp() throws Exception {
        executor = new Executor();
    }

    @Test
    public void testExecute() {
        List<Case> caseList = Arrays.asList(getGetCase(), getPostCase());
        List<Result> resultList = executor.execute(caseList);
        Assert.assertEquals(resultList.size(), caseList.size());
    }

    private Case getGetCase() {
        return new Case(1,
                1,
                "https://www.baidu.com",
                HttpMethod.POST,
                "fuzzedFieldName",
                "fuzzer",
                new ArrayList<>()
        );
    }

    private Case getPostCase() {
        return new Case(1,
                1,
                "https://www.baidu.com",
                HttpMethod.GET,
                "fuzzedFieldName",
                "fuzzer",
                new ArrayList<>()
        );
    }

}
