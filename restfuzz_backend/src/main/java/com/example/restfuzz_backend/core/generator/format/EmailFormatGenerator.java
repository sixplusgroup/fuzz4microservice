package com.example.restfuzz_backend.core.generator.format;

import com.github.curiousoddman.rgxgen.RgxGen;

public class EmailFormatGenerator implements FormatGenerator {

    private final String pattern = "^([a-zA-Z0-9_-]){2,4}@([a-zA-Z0-9_-]){2,4}(\\.([a-zA-Z0-9_-]){2,4}){1,3}$";

    @Override
    public String getTotallyWrongValue() {
        return new RgxGen(pattern).generateNotMatching();
    }

    @Override
    public String getAlmostValidValue() {
        return new RgxGen(pattern).generate();
    }

}
