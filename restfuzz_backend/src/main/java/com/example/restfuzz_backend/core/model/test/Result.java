package com.example.restfuzz_backend.core.model.test;

import com.alibaba.fastjson.JSONObject;
import com.example.restfuzz_backend.core.enums.HttpMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class Result {

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

    // <参数名, 参数值>
    @Getter
    private Map<String, Object> params;

    // 响应状态码
    @Getter
    private int responseStatusCode;

    // success or warn or fail
    @Getter
    private String result;

    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("caseId", this.id);
        jsonObject.put("round", this.round);
        jsonObject.put("result", this.result);
        jsonObject.put("responseStatusCode", this.responseStatusCode);
        jsonObject.put("url", this.url);
        jsonObject.put("httpMethod", this.httpMethod);
        jsonObject.put("fuzzedFieldName", this.fuzzedFieldName);
        jsonObject.put("fuzzer", this.fuzzer);
        JSONObject params = new JSONObject();
        for (String name : this.params.keySet()) {
            params.put(name, this.params.get(name));
        }
        jsonObject.put("params", params);
        return jsonObject;
    }

}
