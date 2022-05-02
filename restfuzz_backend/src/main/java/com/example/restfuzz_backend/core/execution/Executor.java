package com.example.restfuzz_backend.core.execution;

import com.example.restfuzz_backend.core.model.test.Case;
import com.example.restfuzz_backend.core.model.test.Result;
import com.example.restfuzz_backend.core.util.RequestParamUtil;
import com.example.restfuzz_backend.core.util.ResponseCodeUtil;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

/**
 * 该类用于向被测 api发送 http请求，执行测试
 */
public class Executor {

    @Getter
    private int success = 0;

    @Getter
    private int warn = 0;

    @Getter
    private int fail = 0;

    @Getter
    private List<Case> failedCaseList;

    @Getter
    private List<Case> succeedCaseList;

    /**
     * 逐个执行 caseList 中的用例
     * @return 各用例的执行结果集合
     */
    public List<Result> execute(List<Case> caseList) {
        failedCaseList = new ArrayList<>();
        succeedCaseList = new ArrayList<>();

        List<Result> resultList = new ArrayList<>();
        for (Case aCase : caseList) {
            Response response = null;
            switch (aCase.getHttpMethod()) {
                case GET:
                    response = executeGET(aCase);
                    break;
                case POST:
                    response = executePOST(aCase);
                    break;
            }
            if (response != null) {
                String testResult;
                if (ResponseCodeUtil.is2XX(response.getStatusCode())) {
                    this.success++;
                    this.succeedCaseList.add(aCase);
                    testResult = "success";
                } else if (ResponseCodeUtil.is4XX(response.getStatusCode())){
                    this.warn++;
                    testResult = "warn";
                } else {
                    this.fail++;
                    this.failedCaseList.add(aCase);
                    testResult = "fail";
                }
                Result result = new Result(
                        aCase.getId(),
                        aCase.getRound(),
                        aCase.getUrl(),
                        aCase.getHttpMethod(),
                        aCase.getFuzzedFieldName(),
                        aCase.getFuzzer(),
                        RequestParamUtil.getRequestParamsByFieldListAsMap(aCase.getParams()),
                        response.getStatusCode(),
                        testResult
                );
                resultList.add(result);
            }
        }
        return resultList;
    }

    /**
     * 处理 GET请求
     */
    private Response executeGET(Case curCase) {
        Map<String, Object> params = RequestParamUtil.getRequestParamsByFieldListAsMap(curCase.getParams());
        return given()
                .queryParams(params)
                .when()
                .get(curCase.getUrl());
    }

    /**
     * 处理 POST请求
     */
    private Response executePOST(Case curCase) {
        String params = RequestParamUtil.getRequestParamsByFieldListAsString(curCase.getParams());
        return given()
                .body(params)
                .when()
                .post(curCase.getUrl());
    }

}
