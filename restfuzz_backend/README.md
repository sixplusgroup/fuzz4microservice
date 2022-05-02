## 交互功能

必填

1. 输入被测目标URL
2. 输入请求body的参数结构

选填

1. 可指定字段进行fuzzing或在fuzzing时忽略
2. 可指定fuzzing策略（待定）

## 项目目录结构

- business：后端接口包
  - controller
  - service
- core：fuzzing测试相关代码包
  - enums：一些常量枚举类，如 HTTP 响应码、请求方法
  - fuzzer：模糊引擎包
  - model：基础结构类，如 request、response

## Fuzzers

### Field Fuzzers

### Header Fuzzers

