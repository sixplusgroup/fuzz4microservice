package com.example.restfuzz_backend.core.generator;

import com.example.restfuzz_backend.core.generator.simple.DecimalGenerator;
import com.example.restfuzz_backend.core.model.field.DecimalField;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class DecimalGeneratorTest {

    private static DecimalField numberNoConstraint;
    private static DecimalField numberWithConstraint;

    @BeforeClass
    public static void beforeClass() {
        // 模拟参数名
        final String DEFAULT_FIELD_NAME = "numberField";

        // 模拟初始值
        final String DEFAULT_SEED = "12.23";

        // 模拟边界值限制
        final double MIN_CONSTRAINT = -242.421;
        final double MAX_CONSTRAINT = 6373.32487;

        numberNoConstraint = new DecimalField(DEFAULT_FIELD_NAME, DEFAULT_SEED);

        numberWithConstraint = new DecimalField(DEFAULT_FIELD_NAME, DEFAULT_SEED);
        numberWithConstraint.setMinValue(MIN_CONSTRAINT);
        numberWithConstraint.setMaxValue(MAX_CONSTRAINT);
    }

    /**
     * 针对有边界值限制的，检测返回值是否超出了边界值
     * 无限制的，检测是否返回了预定义的较大或较小值
     */
    @Test
    public void testGenerateLeftBoundaryDecimal() {
        Assert.assertTrue(aLessThanB(
                DecimalGenerator.generateLeftBoundaryDecimal(numberWithConstraint),
                String.valueOf(numberWithConstraint.getMinValue())));

        Assert.assertEquals(String.valueOf(DecimalGenerator.MIN_DECIMAL),
                DecimalGenerator.generateLeftBoundaryDecimal(numberNoConstraint));
    }

    @Test
    public void testGenerateRightBoundaryDecimal() {
        Assert.assertTrue(aGreaterThanB(
                DecimalGenerator.generateRightBoundaryDecimal(numberWithConstraint),
                String.valueOf(numberWithConstraint.getMaxValue())));

        Assert.assertEquals(String.valueOf(DecimalGenerator.MAX_DECIMAL),
                DecimalGenerator.generateRightBoundaryDecimal(numberNoConstraint));
    }

    private boolean aLessThanB(String a, String b) {
        return Double.parseDouble(a) < Double.parseDouble(b);
    }

    private boolean aGreaterThanB(String a, String b) {
        return Double.parseDouble(a) > Double.parseDouble(b);
    }

}
