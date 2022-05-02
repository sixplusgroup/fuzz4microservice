<template>
  <a-layout>
    <PageHeader></PageHeader>

    <a-layout-content style="background-color: white" class="ant-layout-content">

      <!--      测试结果数据总览    -->
      <div style="font-weight: bold">
        <span class="head-tag" style="font-size: 16px">测试数据 :</span>
        <a-tag class="head-tag" color="rgb(0,0,0)">Total : {{report.totalCaseNumber}}</a-tag>
        <a-tag class="head-tag" color="green">Success : {{report.successCaseNumber}}</a-tag>
        <a-tag class="head-tag" color="yellow">Warn : {{report.warnCaseNumber}}</a-tag>
        <a-tag class="head-tag" color="red">Fail : {{report.failCaseNumber}}</a-tag>
        <a-tag class="head-tag">Elapsed Time : {{report.executionTimeInMs}} ms</a-tag>
        <a-tag class="head-tag">Finished at : {{report.finishTime}}</a-tag>
        <a-tooltip title="将测试报告导出为Json文件">
          <a-icon type="download" v-on:click="exportReportAsJsonFile" style="font-size: 22px; float: right"/>
        </a-tooltip>

        <a-select size="small" :default-value="testResultForFilter" style="width: 90px" @change="changeTestResultForFilter">
          <a-select-option value="all">All</a-select-option>
          <a-select-option value="success">Success</a-select-option>
          <a-select-option value="warn">Warn</a-select-option>
          <a-select-option value="fail">Fail</a-select-option>
        </a-select>
      </div>

      <!--      测试结果列表    -->
      <a-table :columns="columns"
               :data-source="resultListInTable"
               size="small"
               :pagination="reportTablePagination"
               style="font-weight: bold; margin-top: 10px">
        <span slot="id" slot-scope="record">
          Case {{record.id}}
        </span>
        <span slot="result" slot-scope="record">
          <a-tag class="result-tag" v-if="record.result === 'success'" color="rgb(38,154,39)">
            {{record.result}}
          </a-tag>
          <a-tag class="result-tag" v-else-if="record.result === 'warn'" color="#C5B314">
            {{record.result}}
          </a-tag>
          <a-tag class="result-tag" v-else-if="record.result === 'fail'" color="rgb(199,26,0)">
            {{record.result}}
          </a-tag>
        </span>
        <span slot="responseStatusCode" slot-scope="record">
          {{record.responseStatusCode}}
        </span>
        <span slot="action" slot-scope="record">
          <a style="color: rgb(52,122,182)" v-on:click="toCaseDetail(record)">查看详情</a>
        </span>
      </a-table>

      <a-divider><span style="color: grey; font-weight: lighter; font-size: 15px">没有更多了</span></a-divider>

    </a-layout-content>
  </a-layout>
</template>

<script>
  import PageHeader from "../components/PageHeader";
  import FileSaver from 'file-saver'

  // 测试报告表头
  const columns = [
    {
      title: '编号',
      scopedSlots: {customRender: 'id'}
    },
    {
      title: '测试结果',
      scopedSlots: {customRender: 'result'}
    },
    {
      title: '响应状态码',
      scopedSlots: {customRender: 'responseStatusCode'},
    },
    {
      title: '变异参数',
      dataIndex: 'fuzzedFieldName',
      filters: [],
      onFilter: function (value, record) {
        return record.fuzzedFieldName === value;
      }
    },
    {
      title: '变异方式',
      dataIndex: 'fuzzer',
      filters: [],
      onFilter: function (value, record) {
        return record.fuzzer === value;
      }
    },
    {
      title: '',
      scopedSlots: {customRender: 'action'},
    }
  ];

  export default {
    name: "Report",
    components: {PageHeader},
    data() {
      return {
        // 结果列表是否分页
        reportTablePagination: false,
        report: {
          totalCaseNumber: 0,
          successCaseNumber: 0,
          warnCaseNumber: 0,
          failCaseNumber: 0,
          executionTimeInMs: 0,
          finishTime: '',
          resultList: []
        },
        // 列表表格中使用的resultList
        resultListInTable: [],
        // 按测试结果筛选
        testResultForFilter: 'all',
        columns
      }
    },
    mounted() {
      this.report = JSON.parse(localStorage.getItem('report'));
      // 给每一条记录加key，值等于CaseId，以防前端报错
      this.report.resultList.forEach(function (result) {
        result['key'] = result.id;
      });
      this.resultListInTable = this.report.resultList;
      // 设置 “变异参数”列 和 “变异方式”列 的筛选条件list
      let fuzzedFieldNameFilters = [];
      let fuzzerFilters = [];
      let fuzzerList = [];
      for (let key in this.resultListInTable[0].params) {
        fuzzedFieldNameFilters.push({
          text: key,
          value: key
        })
      }
      for (let result in this.resultListInTable) {
        let curFuzzer = this.resultListInTable[result].fuzzer;
        if (!fuzzerList.includes(curFuzzer)) {
          fuzzerList.push(curFuzzer)
        }
      }
      fuzzerList.forEach(function (fuzzer) {
        fuzzerFilters.push({
          text: fuzzer,
          value: fuzzer
        })
      });
      columns[3].filters = fuzzedFieldNameFilters;
      columns[4].filters = fuzzerFilters;
    },
    methods: {
      /**
       * 打开新窗口，用于显示所选用例详情
       */
      toCaseDetail(record) {
        let routeUrl = this.$router.resolve({
          name: 'CaseDetail',
        });
        console.log(record.id);
        localStorage.setItem('curCaseId', record.id);
        window.open(routeUrl.href, '_blank');
      },
      /**
       * 将测试报告导出为report.json文件
       */
      exportReportAsJsonFile() {
        const data = JSON.stringify(this.report, null, 2);
        const blob = new Blob([data], {type: ''});
        FileSaver.saveAs(blob, 'report.json')
      },
      /**
       * 按测试结果筛选条件改变时调用
       */
      changeTestResultForFilter(v) {
        if (v === 'all') {
          this.resultListInTable = this.report.resultList;
        } else {
          let listAfterFilter = [];
          this.report.resultList.forEach(function (result) {
            if (result.result === v) {
              listAfterFilter.push(result);
            }
          });
          this.resultListInTable = listAfterFilter;
        }
      }
    }
  }
</script>

<style scoped>
  .ant-layout-content {
    padding: 70px 120px;
    margin-top: 64px;
  }

  /*测试结果tag*/
  .result-tag {
    border-radius: 15px;
    font-size: 14px;
  }

  /*表格上方的数据tag*/
  .head-tag {
    margin-right: 17px;
  }
</style>
