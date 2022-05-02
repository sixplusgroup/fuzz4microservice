<template>
  <a-layout>
    <PageHeader></PageHeader>

    <a-layout-content style="background-color: white" class="ant-layout-content">

      <div style="margin-left: 110px">
        <div class="report-line">
          <span style="font-size: 22px; font-weight: bold">Case {{this.curCase.id}}</span>
        </div>

        <a-divider style="margin: 20px 0; font-weight: bold"/>

        <div class="report-line">
          <span class="report-item-label">测试结果 : </span>
          <a-tag class="result-tag" v-if="this.curCase.result === 'success'" color="rgb(38,154,39)">
            {{this.curCase.result}}
          </a-tag>
          <a-tag class="result-tag" v-else-if="this.curCase.result === 'warn'" color="#C5B314">
            {{this.curCase.result}}
          </a-tag>
          <a-tag class="result-tag" v-else-if="this.curCase.result === 'fail'" color="rgb(199,26,0)">
            {{this.curCase.result}}
          </a-tag>
        </div>

        <div class="report-line">
          <span class="report-item-label">响应状态码 : </span>
          <span class="report-item-content">{{this.curCase.responseStatusCode}}</span>
        </div>

        <div class="report-line">
          <span class="report-item-label">URL : </span>
          <span class="report-item-content">{{this.curCase.url}}</span>
        </div>

        <div class="report-line">
          <span class="report-item-label">请求方法 : </span>
          <i class="report-item-content" style="color: #71097c">{{this.curCase.httpMethod}}</i>
        </div>

        <div class="report-line">
          <span class="report-item-label">变异参数 : </span>
          <span class="report-item-content">{{this.curCase.fuzzedFieldName}}</span>
        </div>

        <div class="report-line">
          <span class="report-item-label">变异方式 : </span>
          <span class="report-item-content">{{this.curCase.fuzzer}}</span>
        </div>

        <div class="report-line">
          <span class="report-item-label" style="vertical-align: top">请求参数 : </span>
          <pre class="report-item-content"
               style="display: inline-block">{{JSON.stringify(this.curCase.params,null,2)}}</pre>
        </div>
      </div>

    </a-layout-content>
  </a-layout>
</template>

<script>
  import PageHeader from "@/components/PageHeader";

  export default {
    name: "CaseDetail",
    components: {PageHeader},
    data() {
      return {
        curCase: {
          id: '',
          result: '',
          responseStatusCode: '',
          url: '',
          httpMethod: '',
          fuzzedFieldName: '',
          fuzzer: '',
          params: {}
        }
      }
    },
    mounted() {
      let report = JSON.parse(localStorage.getItem('report'));
      let curCaseId = localStorage.getItem('curCaseId');
      let resultList = report.resultList;
      this.curCase = resultList[curCaseId - 1];
    },
    methods: {}
  }
</script>

<style scoped>
  .ant-layout-content {
    padding: 70px 120px;
    margin-top: 64px;
  }

  .report-line {
    margin-bottom: 15px;
  }

  .report-item-label {
    font-size: 18px;
    font-weight: bold;
  }

  .report-item-content {
    font-size: 16px;
    font-weight: bold;
  }

  .result-tag {
    border-radius: 15px;
    font-size: 16px;
    font-weight: bold;
  }
</style>
