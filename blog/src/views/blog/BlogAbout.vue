<template>
  <el-card class="m-radius-small">
    <el-image :src="meUrl" fit="fill"></el-image>
  </el-card>
</template>

<script>
export default {
  name: "BlogAbout",
  data() {
    return {
      meUrl: require("@/assets/images/me.png")
    };
  }
};
</script>

<style scoped></style>
