import Vue from 'vue'
import VueRouter from 'vue-router'
import Report from "../views/Report";
import Workspace from "../views/Workspace";
import CaseDetail from "../views/CaseDetail";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    redirect: '/workspace'
  },
  {
    path: '/workspace',
    name: 'Workspace',
    component: Workspace
  },
  {
    path: '/report',
    name: 'Report',
    component: Report
  },
  {
    path: '/caseDetail',
    name: 'CaseDetail',
    component: CaseDetail
  }
];

const router = new VueRouter({
  routes
});

export default router;
