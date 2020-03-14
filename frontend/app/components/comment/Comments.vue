<template>
  <section v-if="comments && comments.length" class="wrapper">
    <h1 class="title">コメント</h1>
    <div class="comments">
      <Comment v-for="(comment, i) in comments" :key="i" :comment="comment" :post="post" :reply="true"/>
    </div>
  </section>
</template>

<script>
import Comment from "~/components/comment/Comment";
import $ from "jquery";
export default {
  components: { Comment },
  props: ["post"],
  data() {
    return {
      comments: []
    }
  },
  mounted() {
   this.$axios.get(process.env.AXIOS_URL + "/post/" + this.post.id + "/comments").then((res) => {
        this.comments = res.data;
    });
  }
};
</script>

<style lang="scss" scoped>
.wrapper {
  margin-top: 1em;
  margin-bottom: 50px;
  background: white;
  max-width: 768px;
  border-radius: 10px;

  .title {
    padding-top: .5em;
    margin-left: 1em;
  }

  .label {
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
  .comments {
    margin-top: .5em;
    margin-right: 1em;
    margin-left: 1em;
    padding-bottom: 10px;
  }
}
</style>