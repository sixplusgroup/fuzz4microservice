package com.example.restfuzz_backend.core.util;

import com.alibaba.fastjson.JSONObject;
import com.example.restfuzz_backend.core.model.field.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestParamUtilTest {

    private static List<Field> fieldList = new ArrayList<>();

    @BeforeClass
    public static void beforeClass() {
        Field stringField = new StringField("name", "book");
        Field integerField = new IntegerField("quantity", "34");
        Field decimalField = new DecimalField("price", "15.7");
        Field booleanField = new BooleanField("isNovel", "true");
        fieldList.add(stringField);
        fieldList.add(integerField);
        fieldList.add(decimalField);
        fieldList.add(booleanField);
    }

    @Test
    public void testGetRequestParamsByFieldListAsJsonObject() {
        JSONObject actual = RequestParamUtil.getRequestParamsByFieldListAsJsonObject(fieldList);
        System.out.println(actual);
        Assert.assertNotNull(actual);
    }

    @Test
    public void testGetRequestParamsByFieldListAsString() {
        String actual = RequestParamUtil.getRequestParamsByFieldListAsString(fieldList);
        System.out.println(actual);
        Assert.assertNotNull(actual);
    }

    @Test
    public void testGetRequestParamsByFieldListAsMap() {
        Map<String, Object> actual = RequestParamUtil.getRequestParamsByFieldListAsMap(fieldList);
        System.out.println(actual);
        Assert.assertNotNull(actual);
    }

}
