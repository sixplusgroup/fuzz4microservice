package com.example.restfuzz_backend.core.generator;

import com.example.restfuzz_backend.core.generator.simple.IntegerGenerator;
import com.example.restfuzz_backend.core.model.field.IntegerField;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IntegerGeneratorTest {

    private static IntegerField numberNoConstraint;
    private static IntegerField numberWithConstraint;

    @BeforeClass
    public static void beforeClass() {
        // 模拟参数名
        final String DEFAULT_FIELD_NAME = "numberField";

        // 模拟初始值
        final String DEFAULT_SEED = "1223";

        // 模拟边界值限制
        final long MIN_CONSTRAINT = -50;
        final long MAX_CONSTRAINT = 10000;

        numberNoConstraint = new IntegerField(DEFAULT_FIELD_NAME, DEFAULT_SEED);

        numberWithConstraint = new IntegerField(DEFAULT_FIELD_NAME, DEFAULT_SEED);
        numberWithConstraint.setMinValue(MIN_CONSTRAINT);
        numberWithConstraint.setMaxValue(MAX_CONSTRAINT);
    }

    /**
     * 针对有边界值限制的，检测返回值是否超出了边界值
     * 无限制的，检测是否返回了预定义的较大或较小值
     */
    @Test
    public void testGenerateLeftBoundaryInteger() {
        Assert.assertTrue(aLessThanB(
                IntegerGenerator.generateLeftBoundaryInteger(numberWithConstraint),
                String.valueOf(numberWithConstraint.getMinValue())));

        Assert.assertEquals(String.valueOf(IntegerGenerator.MIN_INTEGER),
                IntegerGenerator.generateLeftBoundaryInteger(numberNoConstraint));
    }

    @Test
    public void testGenerateRightBoundaryInteger() {
        Assert.assertTrue(aGreaterThanB(
                IntegerGenerator.generateRightBoundaryInteger(numberWithConstraint),
                String.valueOf(numberWithConstraint.getMaxValue())));

        Assert.assertEquals(String.valueOf(IntegerGenerator.MAX_INTEGER),
                IntegerGenerator.generateRightBoundaryInteger(numberNoConstraint));
    }

    private boolean aLessThanB(String a, String b) {
        return Long.parseLong(a) < Long.parseLong(b);
    }

    private boolean aGreaterThanB(String a, String b) {
        return Long.parseLong(a) > Long.parseLong(b);
    }

}
