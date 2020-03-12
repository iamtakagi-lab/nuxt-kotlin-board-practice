<template>
  <section class="hero">
    <div class="content">
      <p>
         人と人が繋がる
      </p>
      <p>
        あつまれどうぶつの森 交流掲示板
      </p>
      <button class="post-button" style="margin-top: 15px" @click="$router.push('/post')">
         募集を投稿する！
      </button>
    </div>
  </section>
</template>
<script>
export default {
  components: {}
};
</script>

<style scoped lang="scss">
.hero {
  width: 100%;
  height: 100%;
  background-size: cover;
  background: url("~assets/img/jpg/hero2.jpg") no-repeat;
  position: relative;
  z-index: 0;
  overflow: hidden; /*ブラー効果でボヤけた部分を非表示*/

  .content {
    text-align: center;
    margin: 100px 0 100px;
    p {
      @media screen and (max-width: 768px) {
        font-size: 23px;
      }
      font-family: "Cinzel", serif;
      font-size: 34px;
      font-weight: bold;
      color: #fff;
      text-shadow: 0 0 20px rgba(0, 0, 0, 0.4);
    }
  }
}

.hero:before {
  content: "";
  background: inherit; /*.bgImageで設定した背景画像を継承する*/
  -webkit-filter: blur(5px);
  -moz-filter: blur(5px);
  -o-filter: blur(5px);
  -ms-filter: blur(5px);
  filter: blur(5px);
  position: absolute;
  /*ブラー効果で画像の端がボヤけた分だけ位置を調整*/
  top: -5px;
  left: -5px;
  right: -5px;
  bottom: -5px;
  z-index: -1; /*重なり順序を一番下にしておく*/
}
</style>
