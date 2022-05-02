package com.example.restfuzz_backend.core.generator.format;

/**
 * 生成有特殊格式的字符串case
 */
public interface FormatGenerator {

    String getTotallyWrongValue();

    String getAlmostValidValue();

}
