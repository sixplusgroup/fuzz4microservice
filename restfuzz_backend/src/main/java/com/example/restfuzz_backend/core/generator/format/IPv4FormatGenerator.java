package com.example.restfuzz_backend.core.generator.format;

import com.github.curiousoddman.rgxgen.RgxGen;

public class IPv4FormatGenerator implements FormatGenerator {

    private final String pattern = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";

    @Override
    public String getTotallyWrongValue() {
        return new RgxGen(pattern).generateNotMatching();
    }

    @Override
    public String getAlmostValidValue() {
        return new RgxGen(pattern).generate();
    }
}
