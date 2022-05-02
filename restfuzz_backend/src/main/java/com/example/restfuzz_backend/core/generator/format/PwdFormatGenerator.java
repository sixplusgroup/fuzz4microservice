package com.example.restfuzz_backend.core.generator.format;

import com.github.curiousoddman.rgxgen.RgxGen;

public class PwdFormatGenerator implements FormatGenerator {

    private final String pattern = "(\\d|[a-z]|[A-Z]|~|!|@|#|$|%|^|&){8,16}";

    @Override
    public String getTotallyWrongValue() {
        return new RgxGen(pattern).generateNotMatching();
    }

    @Override
    public String getAlmostValidValue() {
        return new RgxGen(pattern).generate();
    }

}
