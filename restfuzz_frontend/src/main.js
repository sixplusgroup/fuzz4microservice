import Vue from 'vue'
import App from './App.vue'
import router from './route'
import store from './store'
import axios from "axios";
import VueAxios from "vue-axios";
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css'

Vue.config.productionTip = false;

Vue.use(Antd);
Vue.use(VueAxios, axios);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
