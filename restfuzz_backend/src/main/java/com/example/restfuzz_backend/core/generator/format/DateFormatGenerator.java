package com.example.restfuzz_backend.core.generator.format;

import com.github.curiousoddman.rgxgen.RgxGen;

public class DateFormatGenerator implements FormatGenerator {

    // YY-MM-DD
    private final String pattern = "[1-9][0-9]{3,3}-[01][0-9]-[0-3][0-9]";

    @Override
    public String getTotallyWrongValue() {
        return new RgxGen(pattern).generateNotMatching();
    }

    @Override
    public String getAlmostValidValue() {
        return new RgxGen(pattern).generate();
    }
}
