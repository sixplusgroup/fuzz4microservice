package com.example.restfuzz_backend.business.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.restfuzz_backend.business.service.RestFuzzService;
import com.example.restfuzz_backend.business.serviceImplement.RestFuzzServiceImplement;
import com.example.restfuzz_backend.core.enums.HttpMethod;
import com.example.restfuzz_backend.core.model.field.Field;
import com.example.restfuzz_backend.core.model.test.Report;
import com.example.restfuzz_backend.core.util.FieldUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Random;

@CrossOrigin
@RestController
@RequestMapping("/restfuzz")
public class RestFuzzController {

    private RestFuzzService restFuzzService;

    @Autowired
    public RestFuzzController(RestFuzzServiceImplement restFuzzServiceImplement) {
        this.restFuzzService = restFuzzServiceImplement;
    }

    /**
     * 接收用户在前端输入的被测API的信息
     *
     * @param body {
     *             "httpMethod": "",
     *             "url": "",
     *             "params": [{name: '', type: '', seed: '', max: '', min: '', stringForm: ''}, {}, ...]
     *             }
     */
    @PostMapping("/receive")
    public Report receive(@RequestBody String body) {
        JSONObject apiInfo = JSON.parseObject(body);
        HttpMethod httpMethod = HttpMethod.valueOf(HttpMethod.class, apiInfo.getString("httpMethod").toUpperCase());
        String url = apiInfo.getString("url");
        ArrayList<Field> paramList = new ArrayList<>();
        for (Object params : apiInfo.getJSONArray("params")) {
            JSONObject param = JSON.parseObject(params.toString());
            Field field = FieldUtil.getFieldByJsonInput(param);
            paramList.add(field);
        }
        return restFuzzService.performFuzzTest(url, httpMethod, paramList);
    }

    @GetMapping("/mock")
    public void mock() {
        throw new ArrayIndexOutOfBoundsException();
    }

    @GetMapping("/getMock")
    public void getMock(@PathParam("name") String name, @PathParam("age") int age) {
        if (new Random().nextInt(10) < 5) {
            throw new ArrayIndexOutOfBoundsException();
        }
        System.out.println("getMock: " + name + ", " + age);
    }

    @PostMapping("/postMock")
    public void postMock(@RequestBody String body) {
        if (new Random().nextInt(10) < 5) {
            throw new ArrayIndexOutOfBoundsException();
        }
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println("postMock: " + jsonObject.getString("name") + ", " + jsonObject.getString("age"));
    }

}
