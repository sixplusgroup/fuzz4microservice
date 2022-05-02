package com.example.restfuzz_backend.core.generator.format;

import com.github.curiousoddman.rgxgen.RgxGen;

public class IPv6FormatGenerator implements FormatGenerator {

    private final String pattern = "(([a-f0-9]{1,4}:|){0,7}[::]{0,1}[a-f0-9]{1,4})/\\d{0,3})";

    @Override
    public String getTotallyWrongValue() {
        return new RgxGen(pattern).generateNotMatching();
    }

    @Override
    public String getAlmostValidValue() {
        return new RgxGen(pattern).generate();
    }

}
