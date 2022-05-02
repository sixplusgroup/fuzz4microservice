package com.example.restfuzz_backend.core.generator;

import com.example.restfuzz_backend.core.enums.StringFieldFormat;
import com.example.restfuzz_backend.core.generator.simple.StringGenerator;
import com.example.restfuzz_backend.core.model.field.StringField;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.regex.Pattern;

public class StringGeneratorTest {

    // 模拟参数名
    private static final String DEFAULT_FIELD_NAME = "stringField";

    // 模拟初始值
    private static final String DEFAULT_SEED = "rest fuzz";

    private static StringField stringNoneFormatNoConstraint;
    private static StringField stringNoneFormatWithConstraint;

    @BeforeClass
    public static void beforeClass() {
        // 模拟长度限制
        final long MIN_LENGTH_CONSTRAINT = 10;
        final long MAX_LENGTH_CONSTRAINT = 200;

        stringNoneFormatNoConstraint = new StringField(DEFAULT_FIELD_NAME, DEFAULT_SEED);

        stringNoneFormatWithConstraint = new StringField(DEFAULT_FIELD_NAME, DEFAULT_SEED);
        stringNoneFormatWithConstraint.setMinLength(MIN_LENGTH_CONSTRAINT);
        stringNoneFormatWithConstraint.setMaxLength(MAX_LENGTH_CONSTRAINT);
    }

    @Test
    public void testGenerateWrongCaseForFieldWithFormat() {
        for (StringFieldFormat value : StringFieldFormat.values()) {
            StringField stringWithFormat = new StringField(DEFAULT_FIELD_NAME, DEFAULT_SEED, value);
            String s = StringGenerator.generateTotallyWrongCaseForFieldWithFormat(stringWithFormat);
            Assert.assertNotNull(s);
        }
    }

    @Test
    public void testGenerateAlmostValidCaseForFieldWithFormat() {
        for (StringFieldFormat value : StringFieldFormat.values()) {
            StringField stringWithFormat = new StringField(DEFAULT_FIELD_NAME, DEFAULT_SEED, value);
            String s = StringGenerator.generateAlmostValidCaseForFieldWithFormat(stringWithFormat);
            Assert.assertNotNull(s);
        }
    }

    @Test
    public void testGenerateLongerCase() {
        String s1 = StringGenerator.generateLongerCase(stringNoneFormatNoConstraint);
        Assert.assertTrue(s1.length() > stringNoneFormatNoConstraint.getSeed().length());

        String s2 = StringGenerator.generateLongerCase(stringNoneFormatWithConstraint);
        Assert.assertTrue(s2.length() > stringNoneFormatWithConstraint.getMaxLength());
    }

    @Test
    public void testGenerateShorterCase() {
        String s1 = StringGenerator.generateShorterCase(stringNoneFormatNoConstraint);
        Assert.assertTrue(s1.length() < stringNoneFormatNoConstraint.getSeed().length());

        String s2 = StringGenerator.generateShorterCase(stringNoneFormatWithConstraint);
        Assert.assertTrue(s2.length() < stringNoneFormatWithConstraint.getMinLength());
    }

    @Test
    public void testGenerateEmptyCase() {
        Assert.assertEquals("", StringGenerator.generateEmptyCase());
    }

    @Test
    public void testGenerateNotNumberRandomCase() {
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        String s = StringGenerator.generateNotNumberRandomCase();
        System.out.println(s);
        boolean matches = pattern.matcher(s).matches();
        Assert.assertFalse(matches);
    }

    @Test
    public void testGenerateNotBooleanRandomCase() {
        Pattern pattern = Pattern.compile("(true)|(false)|(True)|(False)|(TRUE)|(FALSE)");
        String s = StringGenerator.generateNotBooleanRandomCase();
        System.out.println(s);
        boolean matches = pattern.matcher(s).matches();
        Assert.assertFalse(matches);
    }
}
