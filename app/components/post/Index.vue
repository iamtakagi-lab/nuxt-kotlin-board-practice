<template>
    <section class="post">
      <div v-if="post.type === 'trade'" class="label" style="background: #2581dc;">
        <div class="label-text">アイテム交換募集</div>
      </div>

      <div v-else-if="post.type === 'friend'" class="label" style="background: tomato;">
        <div class="label-text">フレンド募集</div>
      </div>

      <Headline :post="post"/>
      <Bodytext :post="post"/>
    </section>
</template>

<script>
import Headline from "~/components/post/Headline";
import Bodytext from "~/components/post/Bodytext";
import Footerline from "~/components/post/Footerline";
export default {
   props: ["post"],
   components: {
    Headline,
    Bodytext,
    Footerline
   }
}
</script>

<style lang="scss" scoped>
.post {
  margin-top: 20px;
  margin-bottom: 50px;
  background: white;
  max-width: 768px;
  margin: 0 auto;
  border-radius: 10px;
  padding-bottom: 10px;

  .label {
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;

    text-align: center;
    background: #2581dc;
    text-decoration: none !important;
    margin: 0 auto;
    transition: 0.4s;
    color: white;
    user-select: none; /* CSS3 */
    -moz-user-select: none; /* Firefox */
    -webkit-user-select: none; /* Safari、Chromeなど */
    -ms-user-select: none; /* IE10かららしい */
    max-width: 100%;
    height: 50px;

    .label-text {
      line-height: 50px;
      font-size: 20px;
      margin: 30px;
    }
  }
}
</style>