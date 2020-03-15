<template>
  <div class="wrapper">
    <CommentReplies v-if="comment" :id="id" :comment="comment" :post="post"/>

    <CommentForm v-if="!reply" :post="post" />
    <CommentForm v-else-if="reply" :post="post" :reply="id" />
  </div>
</template>

<script>
import CommentReplies from "~/components/comment/CommentReplies";
import CommentForm from "~/components/comment/CommentForm";
export default {
  async asyncData({ params, query, store }) {
    return {
      post: store.getters.postById(Number(params.id)),
      id: Number(params.commentid),
      reply: query["reply"] === undefined ? false : Boolean(query["reply"])
    };
  },
  components: {
    CommentReplies,
    CommentForm
  },
  data() {
    return {
      comment: null
    }
  },
  mounted() {
     this.$axios
      .get(process.env.AXIOS_URL + "/post/" + this.id + "/comments")
      .then(res => {
         this.comment = res.data[this.id - 1]
    });
  },
  head() {
    return {
      title: "コメント | あつ森ちゃんねる"
    };
  }
};
</script>
<style lang="scss" scoped>
.wrapper {
  max-width: 768px;
  margin: 0 auto;
  margin-top: 1em;
}
</style>