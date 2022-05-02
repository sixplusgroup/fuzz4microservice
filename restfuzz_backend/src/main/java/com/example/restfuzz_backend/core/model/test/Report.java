package com.example.restfuzz_backend.core.model.test;

import io.restassured.response.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 该类封装了测试结果相关信息，返回给前端用于展示
 */
@AllArgsConstructor
public class Report {

    @Getter
    private String finishTime;

//    @Getter
//    private int defaultFuzzTime;
//
//    @Getter
//    private int subsequentTestRounds;
//
//    @Getter
//    private int subsequentFuzzTime;

    // 用例总数
    @Getter
    private int totalCaseNumber;

    // 成功用例数
    @Getter
    private int successCaseNumber;

    // 警告用例数
    @Getter
    private int warnCaseNumber;

    // 失败用例数
    @Getter
    private int failCaseNumber;

    // 总执行时间
    @Getter
    private long executionTimeInMs;

    // 每个用例的测试结果
    @Getter
    private List<Result> resultList;
}
