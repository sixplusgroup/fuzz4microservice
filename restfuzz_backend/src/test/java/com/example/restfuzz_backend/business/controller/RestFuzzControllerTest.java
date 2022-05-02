package com.example.restfuzz_backend.business.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestFuzzControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        //使用上下文构建mockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testReceive() {
        try {
            mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/restfuzz/receive")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(getParamsAsJsonString())
                    .accept(MediaType.APPLICATION_JSON)
            )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * {
     * "httpMethod": "",
     * "url": "",
     * "params": [{name: '', type: '', seed: '', max: '', min: '', stringForm: ''}, {}, ...]
     * }
     */
    private String getParamsAsJsonString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("httpMethod", "get");
        jsonObject.put("url", "https://www.baidu.com/s");
        JSONArray params = new JSONArray();
        JSONObject nameParam = new JSONObject();
        nameParam.put("name", "wd");
        nameParam.put("seed", "模糊测试");
        nameParam.put("max", "");
        nameParam.put("min", "");
        nameParam.put("stringFormat", "");
        nameParam.put("type", "string");
        params.add(nameParam);
        jsonObject.put("params", params);
        return jsonObject.toJSONString();
    }

}
