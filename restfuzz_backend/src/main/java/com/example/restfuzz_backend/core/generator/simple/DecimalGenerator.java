package com.example.restfuzz_backend.core.generator.simple;

import com.example.restfuzz_backend.core.model.field.DecimalField;

/**
 * 该类用于生成 小数值
 */
public class DecimalGenerator {

    // 最大值 最小值
    public static final double MAX_DECIMAL = Double.MAX_VALUE / 2;    //  1.7976931348623157E308 / 2
    public static final double MIN_DECIMAL = -MAX_DECIMAL;            // -1.7976931348623157E308 / 2

    // 极端正值 极端负值
    public static final double MOST_POSITIVE_DECIMAL = Double.MAX_VALUE;
    public static final double MOST_NEGATIVE_DECIMAL = -MOST_POSITIVE_DECIMAL;

    // 基于边界值的偏移量
    private static final double DECIMAL_GAP = 12.23;

    /**
     * 生成 左边界 小数
     * @return 以字符串形式返回
     */
    public static String generateLeftBoundaryDecimal(DecimalField decimalField) {
        if (decimalField.hasMinConstraint) {
            return String.valueOf(decimalField.getMinValue() - DECIMAL_GAP);
        }
        return String.valueOf(MIN_DECIMAL);
    }

    /**
     * 生成 右边界 小数
     * @return 以字符串形式返回
     */
    public static String generateRightBoundaryDecimal(DecimalField decimalField) {
        if (decimalField.hasMaxConstraint) {
            return String.valueOf(decimalField.getMaxValue() + DECIMAL_GAP);
        }
        return String.valueOf(MAX_DECIMAL);
    }
}
