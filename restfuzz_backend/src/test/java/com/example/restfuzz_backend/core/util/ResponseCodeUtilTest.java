package com.example.restfuzz_backend.core.util;

import org.junit.Assert;
import org.junit.Test;

public class ResponseCodeUtilTest {

    private int[] intCode1XXs = {100, 101};
    private String[] stringCode1XXs = {"100", "101"};

    private int[] intCode2XXs = {200, 201, 202, 203, 204, 205, 206};
    private String[] stringCode2XXs = {"200", "201", "202", "203", "204", "205", "206"};

    private int[] intCode3XXs = {300, 301, 302, 303, 304, 305, 306, 307};
    private String[] stringCode3XXs = {"300", "301", "302", "303", "304", "305", "306", "307"};

    private int[] intCode4XXs = {
            400, 401, 402, 403, 404, 405, 406, 407, 408,
            409, 410, 411, 412, 413, 414, 415, 416, 417};
    private String[] stringCode4XXs = {
            "400", "401", "402", "403", "404", "405", "406", "407", "408",
            "409", "410", "411", "412", "413", "414", "415", "416", "417"};

    private int[] intCode5XXs = {500, 501, 502, 503, 504, 505};
    private String[] stringCode5XXs = {"500", "501", "502", "503", "504", "505"};

    @Test
    public void testIs2XX() {
        for (int i = 0; i < intCode1XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is2XX(intCode1XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is2XX(stringCode1XXs[i]));
        }
        for (int i = 0; i < intCode2XXs.length; i++) {
            Assert.assertTrue(ResponseCodeUtil.is2XX(intCode2XXs[i]));
            Assert.assertTrue(ResponseCodeUtil.is2XX(stringCode2XXs[i]));
        }
        for (int i = 0; i < intCode3XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is2XX(intCode3XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is2XX(stringCode3XXs[i]));
        }
        for (int i = 0; i < intCode4XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is2XX(intCode4XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is2XX(stringCode4XXs[i]));
        }
        for (int i = 0; i < intCode5XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is2XX(intCode5XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is2XX(stringCode5XXs[i]));
        }
    }

    @Test
    public void testIs4XX() {
        for (int i = 0; i < intCode1XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is4XX(intCode1XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is4XX(stringCode1XXs[i]));
        }
        for (int i = 0; i < intCode2XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is4XX(intCode2XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is4XX(stringCode2XXs[i]));
        }
        for (int i = 0; i < intCode3XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is4XX(intCode3XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is4XX(stringCode3XXs[i]));
        }
        for (int i = 0; i < intCode4XXs.length; i++) {
            Assert.assertTrue(ResponseCodeUtil.is4XX(intCode4XXs[i]));
            Assert.assertTrue(ResponseCodeUtil.is4XX(stringCode4XXs[i]));
        }
        for (int i = 0; i < intCode5XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is4XX(intCode5XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is4XX(stringCode5XXs[i]));
        }
    }

    @Test
    public void testIs5XX() {
        for (int i = 0; i < intCode1XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is5XX(intCode1XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is5XX(stringCode1XXs[i]));
        }
        for (int i = 0; i < intCode2XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is5XX(intCode2XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is5XX(stringCode2XXs[i]));
        }
        for (int i = 0; i < intCode3XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is5XX(intCode3XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is5XX(stringCode3XXs[i]));
        }
        for (int i = 0; i < intCode4XXs.length; i++) {
            Assert.assertFalse(ResponseCodeUtil.is5XX(intCode4XXs[i]));
            Assert.assertFalse(ResponseCodeUtil.is5XX(stringCode4XXs[i]));
        }
        for (int i = 0; i < intCode5XXs.length; i++) {
            Assert.assertTrue(ResponseCodeUtil.is5XX(intCode5XXs[i]));
            Assert.assertTrue(ResponseCodeUtil.is5XX(stringCode5XXs[i]));
        }
    }

}
