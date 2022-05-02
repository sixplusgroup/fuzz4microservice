<template>
  <a-layout>
    <PageHeader></PageHeader>

    <a-layout-content style="background-color: white" class="ant-layout-content">
      <div class="box">
        <!--        URL输入框      -->
        <div class="urlAndSend">
          <a-select id="httpMethodSelect" :value="httpMethodSelect" v-on:change="changeHttpMethodSelect" size="large">
            <a-select-option value="get">GET</a-select-option>
            <a-select-option value="post">POST</a-select-option>
          </a-select>
          <a-input id="urlInput" placeholder="URL, 例 https://www.baidu.com/s"></a-input>
          <a-button id="sendButton" type="primary" v-on:click="send" :disabled="sendButtonDisable">
            <svg t="1646726845763" class="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg"
                 p-id="2759" width="27" height="27">
              <path d="M664.88260888 860.06521777a50.98695644 50.98695644 0 0 0 77.1652169-28.79999999l189.86087021-670.89130489A71.17826045 71.17826045 0 0 0 883.26956533 72.56521777a70.55217422 70.55217422 0 0 0-49.06956533 3.75652178L119.13043467 406.34782578a51.26086934 51.26086934 0 0 0-6.73043467 89.3347831l149.24347822 98.41304356a31.30434756 31.30434756 0 1 0 34.43478223-52.27826132l-132.18260801-87.1826089 696.52173868-321.45652177a7.94347822 7.94347822 0 0 1 5.55652177-0.43043467 8.56956533 8.56956533 0 0 1 5.67391289 10.56521777L686.13043467 799.1l-132.45652178-87.37826045a39.13043467 39.13043467 0 0 0-53.53043466 10.09565157l-72.46956534 102.52173954v-205.70869599l274.38260889-226.09565244a31.30434756 31.30434756 0 1 0-39.79565244-48.32608624l-282.91304356 233.13912979a39.13043467 39.13043467 0 0 0-14.282608 30.20869599v290.73913067a39.13043467 39.13043467 0 0 0 71.09999999 22.53912979l102.01304268-144.35217335 126.7434791 83.62173868z"
                    p-id="2760" fill="#ffffff"></path>
            </svg>
          </a-button>
        </div>

        <a-divider style="margin: 20px 0"/>

        <!--        “参数设置”、“添加参数”、“自动识别” 文字    -->
        <div style="margin-left: 45px; display: inline-block">
          <span style="font-size: 18px; font-weight: bold; margin-right: 20px; user-select: none">参数设置</span>

          <div class="addParamIconAndText" v-on:click="addParam" style="display: inline-block">
            <a-icon type="plus" style="font-size: 17px; color: rgb(52,122,182); margin-right: 1px"/>
            <span style="font-size: 16px; font-weight: bold; color: rgb(52,122,182); user-select: none">手动添加</span>
          </div>

          <a-divider type="vertical"/>

          <div class="addParamIconAndText" v-on:click="showScanModal" style="display: inline-block">
            <a-icon type="scan" style="font-size: 17px; color: rgb(52,122,182); margin-right: 1px"/>
            <span style="font-size: 16px; font-weight: bold; color: rgb(52,122,182); user-select: none">自动识别</span>
          </div>
          <a-tooltip title="使用自动识别会覆盖所有已添加的参数信息">
            <a-icon type="question-circle" style="margin-left: 10px; font-size: 15px"/>
          </a-tooltip>

          <a-divider type="vertical"/>

          <div class="addParamIconAndText" v-on:click="clearParamList" style="display: inline-block">
            <a-icon type="delete" style="font-size: 17px; color: rgb(52,122,182); margin-right: 1px"/>
            <span style="font-size: 16px; font-weight: bold; color: rgb(52,122,182); user-select: none">清空参数</span>
          </div>
        </div>

        <!--        参数列表        -->
        <div v-if="paramList.length !== 0">
          <a-table :columns="columns"
                   :data-source="paramList"
                   size="small"
                   :pagination="paramTablePagination"
                   style="margin: 15px 0 0 165px; width: 75%; font-weight: bold; display: inline-block;">
            <span slot="typeTitle">
              参数类型
              <a-tooltip title="括号标出的是String类型的特殊格式">
                <a-icon type="question-circle"></a-icon>
              </a-tooltip>
            </span>
            <span slot="type" slot-scope="record">
              {{record.type}}
              {{record.type === 'string' && (record.stringFormat !== '') ? ' (' + record.stringFormat + ')' : ''}}
            </span>
            <span slot="action" slot-scope="record">
              <a style="color: rgb(52,122,182)" v-on:click="editParam(record)">编辑</a>
              <a-divider type="vertical"/>
              <a-popconfirm title="确认删除该参数？" ok-text="确认" cancel-text="取消" @confirm="deleteParam(record)">
                <a style="color: red">删除</a>
              </a-popconfirm>
            </span>
          </a-table>
          <a-tooltip title="列表中属性为空表示该参数未设置该属性" style="vertical-align: top; margin: 25px 0 0 10px">
            <a-icon type="question-circle" style="font-size: 22px"/>
          </a-tooltip>
        </div>

        <!--        编辑参数侧边栏   -->
        <a-drawer title="添加参数"
                  :visible="drawerVisible"
                  :width="550"
                  v-on:close="closeDrawer">
          <a-form :form="form" @submit="paramEditConfirm">
            <a-form-item v-if="drawerMode === 'add'" label="名称" :label-col="formItemLayout.labelCol">
              <a-input v-decorator="[
                'name', { rules: [{ required: true, message: '' }] }
                ]" placeholder="必填" style="width: 293px; margin-right: 6px"/>
              <a-tooltip title="不能和已有参数名重复">
                <a-icon type="exclamation-circle"/>
              </a-tooltip>
            </a-form-item>
            <a-form-item v-else label="名称" :label-col="formItemLayout.labelCol">
              <span style="width: 293px; margin-right: 6px">{{curParamNameWhileEditing}}</span>
              <a-tooltip title="不能修改参数名">
                <a-icon type="exclamation-circle"/>
              </a-tooltip>
            </a-form-item>
            <a-form-item label="类型" :label-col="formItemLayout.labelCol">
              <a-select v-decorator="[
                'type', { rules: [{ required: true, message: '' }] }
                ]" placeholder="必选" v-on:change="paramTypeSelectChange" style="width: 293px">
                <a-select-option value="string">String(字符串)</a-select-option>
                <a-select-option value="integer">Integer(整数)</a-select-option>
                <a-select-option value="decimal">Decimal(小数)</a-select-option>
                <a-select-option value="boolean">Boolean(布尔)</a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="初始数据" :label-col="formItemLayout.labelCol">
              <a-input v-decorator="[
                'seed', { rules: [{ required: true, message: ''}] }
                ]" style="width: 293px; margin-right: 6px" placeholder="必填，填写该参数的一个合法值"></a-input>
              <a-tooltip title="务必填写合法值，否则会影响测试结果">
                <a-icon type="exclamation-circle"/>
              </a-tooltip>
            </a-form-item>
            <a-form-item label="最大长度/最大值" :label-col="formItemLayout.labelCol">
              <a-input v-decorator="['max']" placeholder="选填，不填视为“无”" :disabled="maxMinInputDisable"
                       style="width: 293px; margin-right: 6px"></a-input>
              <a-tooltip title="特殊格式的String类型无法设置">
                <a-icon type="exclamation-circle"/>
              </a-tooltip>
            </a-form-item>
            <a-form-item label="最小长度/最小值" :label-col="formItemLayout.labelCol">
              <a-input v-decorator="['min']" placeholder="选填，不填视为“无”" :disabled="maxMinInputDisable"
                       style="width: 293px; margin-right: 6px"></a-input>
              <a-tooltip title="Boolean类型及有特殊格式的String类型参数无法设置">
                <a-icon type="exclamation-circle"/>
              </a-tooltip>
            </a-form-item>
            <a-form-item label="特殊格式" :label-col="formItemLayout.labelCol">
              <a-select v-decorator="['stringFormat']" style="width: 293px; margin-right: 6px"
                        placeholder="选填，不选视为“无”" :disabled="stringFormatSelectDisable"
                        v-on:change="stringFormatSelectChange">
                <a-select-option value="date">日期 (YY-MM-DD)</a-select-option>
                <a-select-option value="email">邮箱 (xxx@xxx.xxx)</a-select-option>
                <a-select-option value="idcard">中华人民共和国居民身份证号</a-select-option>
                <a-select-option value="ipv4">IPv4</a-select-option>
                <a-select-option value="ipv6">IPv6</a-select-option>
                <a-select-option value="phone">手机号 (11位)</a-select-option>
                <a-select-option value="pwd">密码</a-select-option>
                <a-select-option value="none">无</a-select-option>
              </a-select>
              <a-tooltip title="参数为String类型时可选">
                <a-icon type="exclamation-circle"/>
              </a-tooltip>
            </a-form-item>
          </a-form>
          <div style="position: absolute; right: 0 ;bottom: 0; width: 100%; text-align: right; padding: 15px 21px">
            <a-divider></a-divider>
            <a-button type="danger" ghost style="margin-right: 7px" v-on:click="closeDrawer">取消</a-button>
            <a-button type="primary" ghost style="margin-right: 7px" v-on:click="resetDrawer">重置</a-button>
            <a-button type="primary" v-on:click="paramEditConfirm">确认</a-button>
          </div>
        </a-drawer>

        <!--        自动识别添加参数    -->
        <a-modal title="自动识别"
                 :visible="scanModalVisible" cancelText="取消" okText="确认"
                 @cancel="closeScanModal"
                 @ok="addParamByScan">
          <span>参数形式 : </span>
          <a-radio-group :default-value="scanType" v-on:change="scanTypeChange">
            <a-radio value="body">Body</a-radio>
            <a-radio value="url">Url</a-radio>
          </a-radio-group>
          <a-tooltip title="请按示例格式输入">
            <a-icon style="font-size: 20px; float: right; margin-left: 10px" type="exclamation-circle"/>
          </a-tooltip>
          <a-tooltip title="清空">
            <a-icon style="font-size: 20px; float: right; margin-left: 10px" type="reload"
                    v-on:click="clearScanModalTextArea"/>
          </a-tooltip>
          <a-tooltip v-if="scanType === 'body'" title="格式化Json">
            <a-icon style="font-size: 20px; float: right" type="file" v-on:click="formatAsJson"/>
          </a-tooltip>
          <a-textarea id="scanModalInput"
                      :placeholder="scanModalTextAreaPlaceholder[scanType]"
                      :auto-size="{ minRows: 18, maxRows: 18 }"
                      style="margin-top: 6px"
                      :value="scanModalTextAreaValue"
                      v-on:change="scanModalTextAreaValueChange">
          </a-textarea>
        </a-modal>

        <a-divider style="margin: 20px 0"></a-divider>

        <!--        “测试进度” 文字 -->
        <div style="margin-left: 45px; display: inline-block">
          <span style="font-size: 18px; font-weight: bold; margin-right: 20px; user-select: none">测试进度</span>
        </div>

        <!--        “未开始测试” 提示  -->
        <a-empty v-if="testMode === 'none'">
          <span slot="description">未开始测试</span>
        </a-empty>

        <!--        “正在测试” 提示   -->
        <div v-else-if="testMode === 'testing'" style="text-align: center">
          <span>正在测试......</span>
        </div>

        <!--        “测试结束” 提示   -->
        <div v-else-if="testMode === 'finish'" style="text-align: center">
          <span style="margin-right: 10px">测试结束</span><a v-on:click="toReport">查看测试报告</a>
        </div>
      </div>
    </a-layout-content>
  </a-layout>
</template>

<script>
  import PageHeader from "../components/PageHeader";
  import {generateSendAPI} from "../api/apis";

  const formItemLayout = {
    labelCol: {span: 6},
    wrapperCol: {span: 15}
  };

  // 参数列表表头
  const columns = [
    {
      title: '参数名',
      dataIndex: 'name',
    },
    {
      slots: {title: 'typeTitle'},
      scopedSlots: {customRender: 'type'},
    },
    {
      title: '初始数据',
      dataIndex: 'seed',
    },
    {
      title: '边界值限制',
      children: [
        {
          title: '最大值(长度)',
          dataIndex: 'max',
        },
        {
          title: '最小值(长度)',
          dataIndex: 'min',
        }
      ]
    },
    {
      title: '',
      scopedSlots: {customRender: 'action'}
    }
  ];

  export default {
    name: "Workspace",
    components: {PageHeader},
    data() {
      return {
        // 选择的 HTTP method
        httpMethodSelect: 'get',
        // 添加的参数
        paramList: [
          {
            key: 'name',
            name: 'name',
            type: 'string',
            seed: 'wangyubo',
            max: '',
            min: '',
            stringFormat: ''
          },
          {
            key: 'age',
            name: 'age',
            type: 'integer',
            seed: 21,
            max: 100,
            min: 0,
            stringFormat: ''
          }
        ],
        // 侧边栏开关
        drawerVisible: false,
        // 侧边栏状态 add-添加参数；edit-编辑参数
        drawerMode: 'add',
        // 编辑参数时，被编辑的参数名
        curParamNameWhileEditing: '',
        // 自动识别参数Modal开关
        scanModalVisible: false,
        // 自动识别textarea值
        scanModalTextAreaValue: '',
        // 自动识别textarea默认提示文本
        scanModalTextAreaPlaceholder: {
          'body': '输入示例 : { "key1": "value1", "key2": "value2" }',
          'url': '输入示例 : ?key1=value1&key2=value2'
        },
        // 自动识别参数形式 body or url
        scanType: 'body',
        // 参数列表所用table组件是否分页
        paramTablePagination: false,
        // 侧边栏中的表单
        form: this.$form.createForm(this, {name: '编辑参数'}),
        // 字符串特殊格式选择框禁用开关
        stringFormatSelectDisable: true,
        // max min输入框禁用开关
        maxMinInputDisable: false,
        // 测试状态 'none'-无测试，'testing'-正在测试，'finish'-测试完成
        testMode: 'none',
        // 发送按钮禁用开关
        sendButtonDisable: false,
        // 后端测试结果
        reportMock: {},
        columns,
        formItemLayout,
      }
    },
    watch: {},
    methods: {
      addParam() {
        this.drawerMode = 'add';
        this.resetDrawer();
        this.showDrawer()
      },
      deleteParam(record) {
        this.paramList = this.paramList.filter(function (param) {
          return param.name !== record.name;
        })
      },
      toReport() {
        let routeUrl = this.$router.resolve({
          name: 'Report',
        });
        localStorage.setItem('report', JSON.stringify(this.reportMock));
        window.open(routeUrl.href, '_blank');
      },
      send() {
        let _this = this;
        let url = document.getElementById('urlInput').value.trim();
        if (url === '') {
          this.$message.error('请填写被测url')
        } else {
          generateSendAPI({
            url: url,
            httpMethod: this.httpMethodSelect,
            params: this.paramList
          }).then(function (response) {
            console.log(response.data);
            if (response.status === 200) {
              _this.testMode = 'finish';
              _this.sendButtonDisable = false;
              // 此处将this.reportMock设为后端返回的测试结果集
              _this.reportMock = response.data;
            }
          });
          this.sendButtonDisable = true;
          this.testMode = 'testing';
        }
      },
      changeHttpMethodSelect(v) {
        this.httpMethodSelect = v;
      },
      showDrawer() {
        this.drawerVisible = true;
      },
      closeDrawer() {
        this.drawerVisible = false;
      },
      showScanModal() {
        this.scanModalVisible = true;
      },
      closeScanModal() {
        this.scanModalVisible = false;
      },
      /**
       * 确认添加参数时调用
       */
      paramEditConfirm() {
        let name = this.drawerMode === 'add' ? this.form.getFieldValue('name') : this.curParamNameWhileEditing;
        let type = this.form.getFieldValue('type');
        let seed = this.form.getFieldValue('seed');
        let max = this.form.getFieldValue('max');
        let min = this.form.getFieldValue('min');
        let stringFormat = this.form.getFieldValue('stringFormat');
        if (this.checkParamInput()) {
          let newParam = {
            key: name,
            name: name,
            type: type,
            seed: seed,
            // 未定义的默认置为空串
            max: typeof max === 'undefined' ? '' : max,
            min: typeof min === 'undefined' ? '' : min,
            stringFormat: typeof stringFormat === 'undefined' ? '' : stringFormat
          };
          console.log('newParam: ', newParam);
          if (this.drawerMode === 'add') {
            this.paramList.push(newParam);
          } else if (this.drawerMode === 'edit') {
            this.paramList.forEach(function (param) {
              if (param.name === newParam.name) {
                param.type = newParam.type;
                param.seed = newParam.seed;
                param.max = newParam.max;
                param.min = newParam.min;
                param.stringFormat = newParam.stringFormat;
              }
            })
          }
          this.resetDrawer();
          this.closeDrawer();
        }
      },
      /**
       * 确认新添加参数时调用，用于校验用户输入的值
       * @return boolean
       */
      checkParamInput() {
        let name = this.drawerMode === 'add' ? this.form.getFieldValue('name') : this.curParamNameWhileEditing;
        let type = this.form.getFieldValue('type');
        let seed = this.form.getFieldValue('seed');
        // 校验参数名
        if ((typeof name === 'undefined') || name === '') {
          this.$message.error('参数名称不能为空！');
          return false;
        }
        // 添加参数时，保证参数名未重复
        if (this.drawerMode === 'add') {
          let foundDuplication = false;
          this.paramList.forEach(function (param) {
            if (name === param.name) {
              foundDuplication = true;
            }
          });
          if (foundDuplication) {
            this.$message.error('参数名不能与已有参数名重复！');
            return false;
          }
        }
        // 校验参数类型
        if (typeof type === 'undefined') {
          this.$message.error('参数类型不能为空！');
          return false;
        }
        // 校验初始数据
        if ((typeof seed === 'undefined' || seed === '')) {
          this.$message.error('初始数据不能为空！');
          return false;
        }
        return true;
      },
      /**
       * 参数类型选择变化时调用
       * @param v select param type
       */
      paramTypeSelectChange(v) {
        // 选择的不是string类型时，禁用stringFormat选择框，并清空选择框的状态
        this.stringFormatSelectDisable = v !== 'string';
        this.form.setFieldsValue({
          'stringFormat': undefined
        });
        // 选择的是boolean类型时，禁用max min输入框
        this.maxMinInputDisable = v === 'boolean';
      },
      /**
       * stringFormat选择变化时调用
       * @param v select string format
       */
      stringFormatSelectChange(v) {
        // 选了format时，禁用max min输入框，并清空max min输入框状态
        this.maxMinInputDisable = v !== 'none';
        if (v !== 'none') {
          this.form.setFieldsValue({
            'max': undefined,
            'min': undefined
          })
        }
      },
      /**
       * 重置侧边栏中的值
       */
      resetDrawer() {
        this.form.resetFields();
        this.maxMinInputDisable = false;
        this.stringFormatSelectDisable = true;
      },
      /**
       * 点击编辑参数时调用
       */
      editParam(record) {
        this.drawerMode = 'edit';
        this.curParamNameWhileEditing = record.name;
        this.resetDrawer();
        this.showDrawer();
        this.$nextTick(() => {
          this.form.setFieldsValue({
            'type': record.type,
            'seed': record.seed,
          });
          let type = record.type;
          let max = record.max;
          let min = record.min;
          let stringFormat = record.stringFormat;
          if (type !== 'string') {
            // 非string，禁用format选择框
            this.stringFormatSelectDisable = true;
            if (type === 'boolean') {
              // boolean，禁用max min
              this.maxMinInputDisable = true;
            } else {
              // integer or decimal
              this.form.setFieldsValue({
                'max': max === '' ? undefined : max,
                'min': min === '' ? undefined : min
              })
            }
          } else {
            // string
            // 解除format选择框禁用
            this.stringFormatSelectDisable = false;
            // 有format的默认禁用max min输入框
            if (stringFormat !== '') {
              this.maxMinInputDisable = true;
            }
            this.form.setFieldsValue({
              'stringFormat': stringFormat === '' ? 'none' : stringFormat
            });
          }
        });
      },
      /**
       * 该方法在 ScanModal TextArea中的输入变化时被调用
       */
      scanModalTextAreaValueChange(v) {
        this.scanModalTextAreaValue = v.target.value;
      },
      /**
       * 该方法用于将输入的body参数格式化为json格式
       */
      formatAsJson() {
        try {
          this.scanModalTextAreaValue = JSON.stringify(JSON.parse(this.scanModalTextAreaValue), null, 4);
        } catch (e) {
          this.$message.error('不符合json格式！')
        }
      },
      /**
       * 该方法用于清空 ScanModal TextArea中的输入
       */
      clearScanModalTextArea() {
        this.scanModalTextAreaValue = '';
      },
      /**
       * 自动识别添加参数
       */
      addParamByScan() {
        let newParamList = [];
        let inputValue = document.getElementById('scanModalInput').value;
        let params;
        if (this.checkScanModalTextAreaValueFormat(inputValue)) {
          if (this.scanType === 'body') {
            params = JSON.parse(inputValue);
          } else if (this.scanType === 'url') {
            params = this.decodeUrlParamsAsMap(inputValue);
          }
          for (let name in params) {
            let seed = params[name];
            let type = this.identifyParamType(seed);
            newParamList.push({
              key: name,
              name: name,
              seed: seed,
              type: type,
              max: '',
              min: '',
              stringFormat: ''
            });
          }
          this.paramList = newParamList;
          this.clearScanModalTextArea();
          this.closeScanModal();
        } else {
          this.$message.error('输入格式错误！')
        }
      },
      /**
       * 该方法用来根据参数初始数据识别参数类型
       * @param value seed
       */
      identifyParamType(value) {
        if (/^-?\d+$/.test(value)) {
          return 'integer';
        } else if (/^(-?\d+)(\.\d+)?$/.test(value)) {
          return 'decimal';
        } else if (/(true|True|TRUE|false|False|FALSE)/.test(value)) {
          return 'boolean';
        } else {
          return 'string';
        }
      },
      /**
       * 该方法在 scanType变化时被调用
       */
      scanTypeChange(v) {
        this.scanType = v.target.value;
      },
      /**
       * 该方法用于把url参数转换为map
       * @param value ?key1=value1&key2=value2&...
       */
      decodeUrlParamsAsMap(value) {
        let paramTuples = value.substring(1).split('&');
        let paramsMap = {};
        paramTuples.forEach(function (tuple) {
          let nameAndSeed = tuple.split('=');
          let name = nameAndSeed[0];
          paramsMap[name] = nameAndSeed[1];
        });
        return paramsMap;
      },
      /**
       * 检查 ScanModal TextArea中输入的值是否符合所选格式，在确认添加之前调用
       */
      checkScanModalTextAreaValueFormat(value) {
        if (/^\?(.+=.+)((&.+=.+)+)?/.test(value)) {
          return true;
        } else {
          try {
            JSON.parse(value);
          } catch (e) {
            return false;
          }
          return true;
        }
      },
      /**
       * 清空已添加的参数信息
       */
      clearParamList() {
        this.paramList = [];
      }
    }
  }
</script>

<style scoped>
  .ant-layout-content {
    padding: 70px 120px;
    margin-top: 64px;
  }

  .urlAndSend {
    text-align: center;
  }

  /*URL输入框*/
  #urlInput {
    width: 45%;
    height: 41px;
    font-weight: bold;
  }

  /*HTTP method选择框*/
  #httpMethodSelect {
    width: 95px;
    font-weight: bold;
  }

  /*发送按钮*/
  #sendButton {
    height: 41px;
    width: 70px;
    vertical-align: top;
    margin-left: 30px;
  }

  /*添加参数的加号和文字，鼠标悬停*/
  .addParamIconAndText:hover {
    cursor: pointer;
  }
</style>
