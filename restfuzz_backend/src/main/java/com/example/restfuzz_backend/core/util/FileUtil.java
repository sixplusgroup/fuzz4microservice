package com.example.restfuzz_backend.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.restfuzz_backend.business.serviceImplement.RestFuzzServiceImplement;
import com.example.restfuzz_backend.core.model.test.Case;
import com.example.restfuzz_backend.core.model.test.Report;
import com.example.restfuzz_backend.core.model.test.Result;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileUtil {

    /**
     * 生成 cases.json 文件
     */
    public static void outputCasesJsonFile(List<Case> caseList) {
        try {
            File file = new File("src/main/tmpFiles/cases.json");
            if (file.exists() || file.createNewFile()) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                JSONArray jsonArray = new JSONArray();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置日期格式
                jsonArray.add(df.format(new Date()));// new Date()为获取当前系统时间
                for (Case aCase : caseList) {
                    jsonArray.add(aCase.toJsonObject());
                }
                String content = JSON.toJSONString(jsonArray, SerializerFeature.PrettyFormat,
                        SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                bw.write(content);
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成 report.json 文件
     */
    public static void outputReportJsonFile(Report report) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("finishTime", report.getFinishTime());
        jsonObject.put("defaultFuzzTime", RestFuzzServiceImplement.DEFAULT_FUZZ_TIME);
        jsonObject.put("subsequentTestRounds", RestFuzzServiceImplement.SUBSEQUENT_TEST_ROUNDS);
        jsonObject.put("subsequentFuzzTime", RestFuzzServiceImplement.SUBSEQUENT_FUZZ_TIME);
        jsonObject.put("totalCaseNumber", report.getTotalCaseNumber());
        jsonObject.put("successCaseNumber", report.getSuccessCaseNumber());
        jsonObject.put("warnCaseNumber", report.getWarnCaseNumber());
        jsonObject.put("failCaseNumber", report.getFailCaseNumber());
        jsonObject.put("executionTimeInMs", report.getExecutionTimeInMs());
        JSONArray resultList = new JSONArray();
        for (Result result : report.getResultList()) {
            resultList.add(result.toJsonObject());
        }
        jsonObject.put("resultList", resultList);
        try {
            File file = new File("src/main/tmpFiles/report.json");
            if (file.exists() || file.createNewFile()) {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                String content = JSON.toJSONString(jsonObject, SerializerFeature.PrettyFormat,
                        SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
                bw.write(content);
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
