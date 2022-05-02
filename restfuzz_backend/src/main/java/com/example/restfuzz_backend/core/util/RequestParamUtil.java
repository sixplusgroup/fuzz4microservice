package com.example.restfuzz_backend.core.util;

import com.alibaba.fastjson.JSONObject;
import com.example.restfuzz_backend.core.model.field.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 该工具类用于生成不同格式的请求参数
 */
public class RequestParamUtil {

    /**
     * 生成cases.json文件时用该方法将Case实例中的成员变量body转为JSONObject
     * @return JSONObject形式的请求参数
     */
    public static JSONObject getRequestParamsByFieldListAsJsonObject(List<Field> fieldList) {
        JSONObject body = new JSONObject();
        for (Field field : fieldList) {
            body.put(field.getFieldName(), field.getFuzzValue());
        }
        return body;
    }

    /**
     * 用于参数位于body中的Http请求类型，例如POST
     * @return 字符串形式的请求参数 "{"name": "value", "name": "value", ...}"
     */
    public static String getRequestParamsByFieldListAsString(List<Field> fieldList) {
        StringBuilder params = new StringBuilder();
        params.append("{");
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            params.append(field.getAsBodyParam());
            if (i == fieldList.size() - 1) {
                params.append("}");
            } else {
                params.append(",");
            }
        }
        return params.toString();
    }

    /**
     * 用于参数位于path中的Http请求类型，例如GET
     * @return Map形式的请求参数
     */
    public static Map<String, Object> getRequestParamsByFieldListAsMap(List<Field> fieldList) {
        Map<String, Object> params = new HashMap<>();
        for (Field field : fieldList) {
            params.put(field.getFieldName(), field.getFuzzValue());
        }
        return params;
    }

}
