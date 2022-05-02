package com.example.restfuzz_backend.core.util;

import com.alibaba.fastjson.JSONObject;
import com.example.restfuzz_backend.core.model.field.Field;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FieldUtilTest {

    private List<JSONObject> paramList;

    @Before
    public void setUp() throws Exception {
        paramList = new ArrayList<>();
        paramList.add(mockStringParam());
        paramList.add(mockStringWithFormatParam());
        paramList.add(mockIntegerParam());
        paramList.add(mockDecimalParam());
        paramList.add(mockBooleanParam());
    }

    @Test
    public void testGetFieldByJsonInput() {
        for (JSONObject param : paramList) {
            Field field = FieldUtil.getFieldByJsonInput(param);
            Assert.assertNotNull(field);
        }
    }

    private JSONObject mockStringParam() {
        JSONObject param = new JSONObject();
        param.put("name", "name");
        param.put("type", "string");
        param.put("seed", "book");
        param.put("max", "30");
        param.put("min", "5");
        param.put("stringFormat", "");
        return param;
    }

    private JSONObject mockStringWithFormatParam() {
        JSONObject param = new JSONObject();
        param.put("name", "address");
        param.put("type", "string");
        param.put("seed", "abc@rr.com");
        param.put("max", "");
        param.put("min", "");
        param.put("stringFormat", "email");
        return param;
    }

    private JSONObject mockIntegerParam() {
        JSONObject param = new JSONObject();
        param.put("name", "quantity");
        param.put("type", "integer");
        param.put("seed", "124");
        param.put("max", "1000");
        param.put("min", "50");
        param.put("stringFormat", "");
        return param;
    }

    private JSONObject mockDecimalParam() {
        JSONObject param = new JSONObject();
        param.put("name", "price");
        param.put("type", "decimal");
        param.put("seed", "15.7");
        param.put("max", "200");
        param.put("min", "10.5");
        param.put("stringFormat", "");
        return param;
    }

    private JSONObject mockBooleanParam() {
        JSONObject param = new JSONObject();
        param.put("name", "isNovel");
        param.put("type", "boolean");
        param.put("seed", "true");
        param.put("max", "");
        param.put("min", "");
        param.put("stringFormat", "");
        return param;
    }

}
