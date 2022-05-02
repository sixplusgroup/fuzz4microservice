<template>
  <a-layout-header class="ant-layout-header">
    <span style="font-size: 35px; font-weight: bold">RESTFUZZ</span>
  </a-layout-header>
</template>

<script>
  export default {
    name: "PageHeader",
    methods: {}
  }
</script>

<style scoped>
  .ant-layout-header {
    background: #7b8c9d;
    padding: 0 15px;
    position: fixed;
    top: 0;
    width: 100%;
    z-index: 999;
  }
</style>
