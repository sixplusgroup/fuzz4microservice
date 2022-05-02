package com.example.restfuzz_backend.business.service;

import com.example.restfuzz_backend.core.enums.HttpMethod;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.model.test.Report;

import java.util.List;

public interface RestFuzzService {

    Report performFuzzTest(String url, HttpMethod httpMethod, List<Field> fieldList);

}
