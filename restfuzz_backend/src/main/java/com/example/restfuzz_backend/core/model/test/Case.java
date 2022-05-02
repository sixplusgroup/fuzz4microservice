package com.example.restfuzz_backend.core.model.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.restfuzz_backend.core.enums.HttpMethod;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.util.RequestParamUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

/**
 * 该类包含了一个完整的模糊测试用例信息
 */
@AllArgsConstructor
public class Case {

    // 用例编号
    @Getter
    private int id;

    @Getter
    private int round;

    @Getter
    private String url;

    @Getter
    private HttpMethod httpMethod;

    // 记录这一个Case中被fuzz的参数名
    @Getter
    private String fuzzedFieldName;

    // 该用例用到的fuzz方式
    @Getter
    private String fuzzer;

    @Getter
    private List<Field> params;

    @Override
    public String toString() {
        return JSON.toJSONString(toJsonObject(), SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
    }

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", this.id);
        jsonObject.put("round", this.round);
        jsonObject.put("url", this.url);
        jsonObject.put("httpMethod", this.httpMethod);
        jsonObject.put("fuzzedFieldName", this.fuzzedFieldName);
        jsonObject.put("fuzzer", this.fuzzer);
        jsonObject.put("params", RequestParamUtil.getRequestParamsByFieldListAsJsonObject(this.params));
        return jsonObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Case aCase = (Case) o;
        return Objects.equals(getUrl(), aCase.getUrl()) &&
                getHttpMethod() == aCase.getHttpMethod() &&
                Objects.equals(getFuzzedFieldName(), aCase.getFuzzedFieldName()) &&
                Objects.equals(getFuzzer(), aCase.getFuzzer()) &&
                Objects.equals(getParams(), aCase.getParams());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUrl(), getHttpMethod(), getFuzzedFieldName(), getFuzzer(), getParams());
    }
}
