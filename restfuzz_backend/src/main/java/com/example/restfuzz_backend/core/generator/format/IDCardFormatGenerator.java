package com.example.restfuzz_backend.core.generator.format;

import com.github.curiousoddman.rgxgen.RgxGen;

public class IDCardFormatGenerator implements FormatGenerator {

    private final String pattern = "[0-9]{17,17}([0-9]|X)";

    @Override
    public String getTotallyWrongValue() {
        return new RgxGen(pattern).generateNotMatching();
    }

    @Override
    public String getAlmostValidValue() {
        return new RgxGen(pattern).generate();
    }
}
