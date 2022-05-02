package com.example.restfuzz_backend.core.generator.format;

import com.github.curiousoddman.rgxgen.RgxGen;

public class NoneFormatGenerator implements FormatGenerator {

    private final String pattern = ".{10,30}";

    @Override
    public String getTotallyWrongValue() {
        return getAlmostValidValue();
    }

    @Override
    public String getAlmostValidValue() {
        return new RgxGen(pattern).generate();
    }

}
