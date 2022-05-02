package com.example.restfuzz_backend.core.util;

import com.alibaba.fastjson.JSONObject;
import com.example.restfuzz_backend.core.enums.StringFieldFormat;
import com.example.restfuzz_backend.core.model.field.*;

/**
 * 该类用于包装生成Field类实例
 */
public class FieldUtil {

    /**
     * 该方法根据从前端接收到的JSON生成Field
     */
    public static Field getFieldByJsonInput(JSONObject param) {
        String name = param.getString("name");
        String type = param.getString("type");
        String seed = param.getString("seed");
        String max = param.getString("max");
        String min = param.getString("min");
        String stringFormat = param.getString("stringFormat");
        Field field = null;
        switch (type) {
            case "string":
                field = new StringField(name, seed);
                if (!stringFormat.equals("") && !stringFormat.equals("none")) {
                    ((StringField) field).setStringFieldFormat(
                            StringFieldFormat.valueOf(StringFieldFormat.class, stringFormat.toUpperCase()));
                }
                if (!max.equals("")) {
                    ((StringField) field).setMaxLength(Long.parseLong(max));
                }
                if (!min.equals("")) {
                    ((StringField) field).setMinLength(Long.parseLong(min));
                }
                break;
            case "integer":
                field = new IntegerField(name, seed);
                if (!max.equals("")) {
                    ((IntegerField) field).setMaxValue(Long.parseLong(max));
                }
                if (!min.equals("")) {
                    ((IntegerField) field).setMinValue(Long.parseLong(min));
                }
                break;
            case "decimal":
                field = new DecimalField(name, seed);
                if (!max.equals("")) {
                    ((DecimalField) field).setMaxValue(Double.parseDouble(max));
                }
                if (!min.equals("")) {
                    ((DecimalField) field).setMinValue(Double.parseDouble(min));
                }
                break;
            case "boolean":
                field = new BooleanField(name, seed);
                break;
        }
        return field;
    }
}
