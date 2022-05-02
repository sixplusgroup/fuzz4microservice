package com.example.restfuzz_backend.core.generator.simple;

import com.example.restfuzz_backend.core.model.field.IntegerField;

import java.math.BigInteger;

/**
 * 该类用于生成 整数值
 */
public class IntegerGenerator {

    // 最大值 最小值
    public static final long MIN_INTEGER = Long.MIN_VALUE / 2;    // -9223372036854775808 / 2
    public static final long MAX_INTEGER = Long.MAX_VALUE / 2;    //  9223372036854775807 / 2

    // 极端正值 极端负值
    public static final long MOST_POSITIVE_INTEGER = Long.MAX_VALUE;
    public static final long MOST_NEGATIVE_INTEGER = Long.MIN_VALUE;

    // 基于边界值的偏移量
    private static final long INTEGER_GAP = 10;

    private static final int ZERO = 0;

    private IntegerGenerator() {}

    /**
     * 该方法生成 左边界 整数
     *
     * @return 以字符串形式返回
     */
    public static String generateLeftBoundaryInteger(IntegerField integerField) {
        if (integerField.hasMinConstraint) {
            return String.valueOf(integerField.getMinValue() - INTEGER_GAP);
        }
        return String.valueOf(MIN_INTEGER);
    }

    /**
     * 该方法生成 右边界 整数
     *
     * @return 以字符串形式返回
     */
    public static String generateRightBoundaryInteger(IntegerField integerField) {
        if (integerField.hasMaxConstraint) {
            return String.valueOf(integerField.getMaxValue() + INTEGER_GAP);
        }
        return String.valueOf(MAX_INTEGER);
    }

}
