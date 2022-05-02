package com.example.restfuzz_backend.business.service;

import com.example.restfuzz_backend.business.serviceImplement.RestFuzzServiceImplement;
import com.example.restfuzz_backend.core.enums.HttpMethod;
import com.example.restfuzz_backend.core.model.field.StringField;
import com.example.restfuzz_backend.core.model.test.Report;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class RestFuzzServiceTest {

    private RestFuzzService restFuzzService = new RestFuzzServiceImplement();

    @Test
    public void testPerformFuzzTest() {
        String url = "https://www.baidu.com/s";
        StringField stringField = new StringField("wd", "模糊测试");
        Report report = restFuzzService.performFuzzTest(url, HttpMethod.GET, Arrays.asList(stringField));
        Assert.assertNotNull(report);
    }

}
