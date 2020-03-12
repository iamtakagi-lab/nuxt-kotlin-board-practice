<template>
  <div class="footerline">
    <ShareButtons :post="post"/>
    <div class="footerline-content">
      <nuxt-link to="/" class="no-decoration no-selection" style="color: black;">募集一覧に戻る</nuxt-link>
    </div>
  </div>
</template>

<script>
import ShareButtons from "~/components/post/ShareButtons";
export default {
  props: ["post"],
  components: {
    ShareButtons
  }
};
</script>

<style lang="scss" scoped>
.footerline {
  margin-top: 20px;
  margin-bottom: 50px;
  background: white;
  max-width: 768px;
  margin: 0 auto;
  text-align: center;
  border-radius: 10px;

  .footerline-content {
    margin-top: 5px;
    margin-bottom: 5px;
    padding: 10px;
  }
}
</style>