package com.example.restfuzz_backend.core.generator.simple;

import com.example.restfuzz_backend.core.generator.format.FormatGenerator;
import com.example.restfuzz_backend.core.model.field.StringField;
import com.github.curiousoddman.rgxgen.RgxGen;

import java.security.SecureRandom;

public class StringGenerator {

    public static final int[] LONG_LENGTH_RANGE = {500, 1000};
    public static final int[] SHORT_LENGTH_RANGE = {1, 20};

    private static final String EMPTY_STRING = "";
    private static final String DEFAULT_PATTERN = ".";
    private static final String NUMBER_PATTERN = "[0-9]";
    private static final String DECIMAL_PATTERN = "^(-?\\d{1,5})\\.(\\d{1,5})$";
    private static final String BOOLEAN_PATTERN = "(true)|(false)|(True)|(False)|(TRUE)|(FALSE)";

    private StringGenerator() {}

    /**
     * 针对有 format 的字段 生成 不符合该格式的case
     */
    public static String generateTotallyWrongCaseForFieldWithFormat(StringField stringField) {
        FormatGenerator formatGenerator = stringField.getStringFieldFormat().getFormatGenerator();
        return formatGenerator.getTotallyWrongValue();
    }

    /**
     * 针对有 format 的字段 生成 基本符合格式的case
     */
    public static String generateAlmostValidCaseForFieldWithFormat(StringField stringField) {
        FormatGenerator formatGenerator = stringField.getStringFieldFormat().getFormatGenerator();
        return formatGenerator.getAlmostValidValue();
    }

    /**
     * 超长 针对无特殊格式的字段
     */
    public static String generateLongerCase(StringField stringField) {
        long seedLen = stringField.getSeed().length();
        String pattern;
        if (stringField.hasMaxConstraint) {
            // 指定了最大长度时就按 (maxLen, maxLen+500] 生成
            long maxLen = stringField.getMaxLength();
            pattern = DEFAULT_PATTERN + "{" + (maxLen + 1) + "," + (maxLen + 500) + "}";
        } else {
            // 没指定就按 (seedLen, seedLen+500] 生成
            pattern = DEFAULT_PATTERN + "{" + (seedLen + 1) + "," + (seedLen + 500)  + "}";
        }
        RgxGen rgxGen = new RgxGen(pattern);
        return rgxGen.generate();
    }

    /**
     * 过短 针对无特殊格式的字段
     */
    public static String generateShorterCase(StringField stringField) {
        long seedLen = stringField.getSeed().length();
        String pattern;
        if (stringField.hasMinConstraint) {
            // 指定了最小长度时就按 [1, minLen) 生成
            long minLen = stringField.getMinLength();
            pattern = DEFAULT_PATTERN + "{1," + (minLen - 1) + "}";
        } else {
            // 没指定就按 [1, seedLen) 生成
            pattern = DEFAULT_PATTERN + "{1," + seedLen + "}";
        }
        RgxGen rgxGen = new RgxGen(pattern);
        return rgxGen.generate();
    }

    /**
     * 空串
     */
    public static String generateEmptyCase() {
        return EMPTY_STRING;
    }

    /**
     * 随机生成非数字字符串
     */
    public static String generateNotNumberRandomCase() {
        String pattern = NUMBER_PATTERN + "{" + SHORT_LENGTH_RANGE[0] + "," + SHORT_LENGTH_RANGE[1] + "}";
        RgxGen rgxGen = new RgxGen(pattern);
        return rgxGen.generateNotMatching();
    }

    /**
     * 随机生成除true/false之外的字符串
     */
    public static String generateNotBooleanRandomCase() {
        RgxGen rgxGen = new RgxGen(BOOLEAN_PATTERN);
        return rgxGen.generateNotMatching();
    }

    /**
     * 随机生成小数
     * @return random decimal as string
     */
    public static String generateDecimalRandomCase() {
        RgxGen rgxGen = new RgxGen(DECIMAL_PATTERN);
        return rgxGen.generate();
    }

    /**
     * 随机生成Unicode字符串
     */
    public static String generateRandomUnicodeCase() {
        SecureRandom random = new SecureRandom();
        // 字符串长度在此指定，150
        int len = 150;
        StringBuilder builder = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            int codePoint = random.nextInt(Character.MAX_CODE_POINT + 1);
            int type = Character.getType(codePoint);
            if (!Character.isDefined(codePoint) || type == Character.PRIVATE_USE ||
                    type == Character.SURROGATE || type == Character.UNASSIGNED) {
                continue;
            }
            builder.appendCodePoint(codePoint);
        }
        return builder.toString();
    }

}
