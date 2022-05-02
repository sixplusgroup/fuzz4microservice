package com.example.restfuzz_backend.core.util;

public class ResponseCodeUtil {

    /**
     * 判断 responseCode是否是 2XX
     */
    public static boolean is2XX(int responseCode) {
        return responseCode / 100 == 2;
    }

    public static boolean is2XX(String responseCode) {
        return responseCode.charAt(0) == '2';
    }

    /**
     * 判断 responseCode是否是 4XX
     */
    public static boolean is4XX(int responseCode) {
        return responseCode / 100 == 4;
    }

    public static boolean is4XX(String responseCode) {
        return responseCode.charAt(0) == '4';
    }

    /**
     * 判断 responseCode是否是 5XX
     */
    public static boolean is5XX(int responseCode) {
        return responseCode / 100 == 5;
    }

    public static boolean is5XX(String responseCode) {
        return responseCode.charAt(0) == '5';
    }

}
