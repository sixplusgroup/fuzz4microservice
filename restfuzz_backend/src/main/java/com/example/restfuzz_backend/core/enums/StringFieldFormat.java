package com.example.restfuzz_backend.core.enums;

import com.example.restfuzz_backend.core.generator.format.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StringFieldFormat {

    DATE ("date", new DateFormatGenerator()),
    EMAIL ("email", new EmailFormatGenerator()),
    IDCARD ("idCard", new IDCardFormatGenerator()),
    IPV4 ("ipv4", new IPv4FormatGenerator()),
    IPV6 ("ipv6", new IPv6FormatGenerator()),
    PHONE ("phone", new PhoneFormatGenerator()),
    PWD ("pwd", new PwdFormatGenerator()),
    NONE ("none", new NoneFormatGenerator());

    @Getter
    private final String format;
    @Getter
    private final FormatGenerator formatGenerator;

}
