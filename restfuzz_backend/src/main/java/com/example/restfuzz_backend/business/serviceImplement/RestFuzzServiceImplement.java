package com.example.restfuzz_backend.business.serviceImplement;

import com.example.restfuzz_backend.business.service.RestFuzzService;
import com.example.restfuzz_backend.core.enums.HttpMethod;
import com.example.restfuzz_backend.core.execution.Executor;
import com.example.restfuzz_backend.core.fuzzer.BaseFuzzer;
import com.example.restfuzz_backend.core.fuzzer.boundary.LeftBoundaryFuzzer;
import com.example.restfuzz_backend.core.fuzzer.boundary.RightBoundaryFuzzer;
import com.example.restfuzz_backend.core.fuzzer.boundary.StringFormatAlmostValidValuesFuzzer;
import com.example.restfuzz_backend.core.fuzzer.extreme.*;
import com.example.restfuzz_backend.core.fuzzer.type.DecimalInIntegerFieldFuzzer;
import com.example.restfuzz_backend.core.fuzzer.type.StringInBooleanFieldFuzzer;
import com.example.restfuzz_backend.core.fuzzer.type.StringInNumberFieldFuzzer;
import com.example.restfuzz_backend.core.model.field.*;
import com.example.restfuzz_backend.core.model.test.Case;
import com.example.restfuzz_backend.core.model.test.Report;
import com.example.restfuzz_backend.core.model.test.Result;
import com.example.restfuzz_backend.core.util.FileUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RestFuzzServiceImplement implements RestFuzzService {

    // 默认轮次（首轮）中使用各fuzzer的fuzz次数
    public static final int DEFAULT_FUZZ_TIME = 1;

    // 后续轮次中，针对succeed cases的fuzz次数
    public static final int SUBSEQUENT_FUZZ_TIME = 2;

    // 后续测试轮次
    public static final int SUBSEQUENT_TEST_ROUNDS = 2;

    // 当前测试轮次序号
    private int currentRound;

    // 生成的总用例数
    private int totalCaseNum;

    @Override
    public Report performFuzzTest(String url, HttpMethod httpMethod, List<Field> fieldList) {
        Executor executor = new Executor();
        totalCaseNum = 0;

        // 开始时间
        long startTime = System.currentTimeMillis();

        // 首轮
        currentRound = 1;
        // 1.生成测试用例集合
        List<Case> wholeCaseList = generateCases(url, httpMethod, fieldList);
        // 2.执行测试用例，并生成测试结果集合
        List<Result> resultList = new ArrayList<>(executor.execute(wholeCaseList));

        // 后续轮次
        for (int i = 0; i < SUBSEQUENT_TEST_ROUNDS; i++) {
            currentRound = 2 + i;
            // 1.针对succeed cases中的fuzzedField用同样的fuzzer生成用例
            List<Case> succeedCaseList = executor.getSucceedCaseList();
            List<Case> curRoundCaseList = new ArrayList<>();
            for (Case succeedCase : succeedCaseList) {
                curRoundCaseList.addAll(generateSimilarCasesWithCertainCase(succeedCase));
            }
            // 2.执行生成的用例，并将执行结果result追加进resultList
            resultList.addAll(executor.execute(curRoundCaseList));

            wholeCaseList.addAll(curRoundCaseList);
        }

        // 结束时间
        long endTime = System.currentTimeMillis();
        long executionTimeInMs = endTime - startTime;

        // 生成测试报告
        Report report = new Report(
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                this.totalCaseNum,
                executor.getSuccess(),
                executor.getWarn(),
                executor.getFail(),
                executionTimeInMs,
                resultList
        );

        // 生成cases.json文件
        FileUtil.outputCasesJsonFile(wholeCaseList);
        // 生成report.json文件
        FileUtil.outputReportJsonFile(report);

        return report;
    }

    /**
     * 该方法用于生成测试用例
     *
     * @return list of cases 测试用例集合
     */
    private List<Case> generateCases(String url, HttpMethod httpMethod, List<Field> fieldList) {
        Map<Field, List<Field>> map = fuzzEachField(fieldList);
        List<Case> caseList = new ArrayList<>();
        for (Field field : map.keySet()) {
            List<Field> list = map.get(field);
            Case newCase = new Case(++this.totalCaseNum,
                    this.currentRound,
                    url,
                    httpMethod,
                    field.getFieldName(),
                    field.getFuzzedBy(),
                    list);
            if (!caseList.contains(newCase)) {
                caseList.add(newCase);
            } else {
                this.totalCaseNum--;
            }
        }
        return caseList;
    }

    /**
     * 针对certain case中的fuzzedField用同样的fuzzer生成用例
     *
     * @return list of cases 测试用例集合
     */
    private List<Case> generateSimilarCasesWithCertainCase(Case certainCase) {
        List<Case> caseList = new ArrayList<>();
        String fuzzedFieldName = certainCase.getFuzzedFieldName();
        BaseFuzzer fuzzer = BaseFuzzer.getFuzzerByDescription(certainCase.getFuzzer());
        List<Field> params = certainCase.getParams();
        if (fuzzer != null) {
            for (int i = 0; i < params.size(); i++) {
                Field field = params.get(i);
                // 找到certainCase被变异的field
                if (field.getFieldName().equals(fuzzedFieldName)) {
                    // 对该field再进行多次fuzz，得到fuzz结果，包装成Case，添加进caseList
                    for (int j = 0; j < SUBSEQUENT_FUZZ_TIME; j++) {
                        Field fuzzedResult = fuzzer.fuzz(field);
                        params.set(i, fuzzedResult);
                        Case newCase = new Case(++this.totalCaseNum,
                                this.currentRound,
                                certainCase.getUrl(),
                                certainCase.getHttpMethod(),
                                certainCase.getFuzzedFieldName(),
                                certainCase.getFuzzer(),
                                params);
                        if (!caseList.contains(newCase)) {
                            caseList.add(newCase);
                        } else {
                            this.totalCaseNum--;
                        }
                    }
                    break;
                }
            }
        }
        return caseList;
    }

    /**
     * 逐个fuzz参数列表中的每一个参数字段
     *
     * @return Map<K, V> => <fuzzedField, field list after fuzzing>
     */
    private Map<Field, List<Field>> fuzzEachField(List<Field> fieldList) {
        Map<Field, List<Field>> res = new HashMap<>();

        // 逐个遍历Field
        for (int i = 0; i < fieldList.size(); i++) {
            Field curField = fieldList.get(i);

            // 当前Field的多个fuzz结果
            List<Field> fuzzResultsOfCurrentField = new ArrayList<>();
            fuzzResultsOfCurrentField.addAll(boundaryFuzz(curField));
            fuzzResultsOfCurrentField.addAll(extremeFuzz(curField));
            fuzzResultsOfCurrentField.addAll(typeFuzz(curField));

            // 将各个fuzz结果替换进原本的fieldList
            for (Field fuzzedField : fuzzResultsOfCurrentField) {
                List<Field> newFieldList = new ArrayList<>(fieldList);
                newFieldList.set(i, fuzzedField);
                res.put(fuzzedField, newFieldList);
            }
        }
        return res;
    }

    private List<Field> boundaryFuzz(Field field) {
        List<Field> res = new ArrayList<>();
        // 有format的String
        if (isStringField(field) && ((StringField) field).hasFormat()) {
            res.addAll(StringFormatAlmostValidValuesFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
        }
        // String, Integer, Decimal
        if (!isBooleanField(field)) {
            res.addAll(LeftBoundaryFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
            res.addAll(RightBoundaryFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
        }
        return res;
    }

    private List<Field> extremeFuzz(Field field) {
        List<Field> res = new ArrayList<>();
        // String
        if (isStringField(field)) {
            if (((StringField) field).hasFormat()) {
                // 有format
                res.addAll(StringFormatTotallyWrongValuesFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
            } else {
                // 没有format
                res.addAll(RandomUnicodeStringFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
            }
        }
        // Integer, Number
        if (isNumberField(field)) {
            res.addAll(ExtremeNegativeNumberValuesFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
            res.addAll(ExtremePositiveNumberValuesFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
        }
        // all
        res.addAll(EmptyValueFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
        return res;
    }

    private List<Field> typeFuzz(Field field) {
        List<Field> res = new ArrayList<>();
        // Integer, Decimal
        if (isNumberField(field)) {
            res.addAll(StringInNumberFieldFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
            // Integer
            if (isIntegerField(field)) {
                res.addAll(DecimalInIntegerFieldFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
            }
        }
        // Boolean
        if (isBooleanField(field)) {
            res.addAll(StringInBooleanFieldFuzzer.getInstance().fuzzKTimes(field, DEFAULT_FUZZ_TIME));
        }
        return res;
    }

    private boolean isNumberField(Field field) {
        return field instanceof IntegerField || field instanceof DecimalField;
    }

    private boolean isStringField(Field field) {
        return field instanceof StringField;
    }

    private boolean isBooleanField(Field field) {
        return field instanceof BooleanField;
    }

    private boolean isIntegerField(Field field) {
        return field instanceof IntegerField;
    }

}
