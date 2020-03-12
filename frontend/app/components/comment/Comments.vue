<template>
  <section v-if="comments.length" class="wrapper">
    
    <div class="comments">
      <Comment v-for="(comment, i) in comments" :key="i" :comment="comment"/>
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
    this.$axios.get(process.env.AXIOS_URL + "/post/" + this.post.id).then((res) => {
        this.comments = res.data.comments
    });
  }
};
</script>

<style lang="scss" scoped>
.wrapper {
  margin-top: 20px;
  margin-bottom: 50px;
  background: white;
  max-width: 768px;
  border-radius: 10px;

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
    margin-top: 20px;
    margin-right: 20px;
    margin-left: 20px;
    padding-bottom: 10px;
  }
}
</style>