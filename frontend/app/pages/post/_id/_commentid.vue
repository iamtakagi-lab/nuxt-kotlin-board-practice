<template>
    <div class="wrapper">
      <CommentReplies :id="id" :replies="replies" :post="post"/>

      <CommentForm v-if="!reply" :post="post"/>
      <CommentForm v-else-if="reply" :post="post" :reply="id"/>
    </div>
</template>

<script>
import CommentReplies from "~/components/comment/CommentReplies";
import CommentForm from "~/components/comment/CommentForm";
export default {
  async asyncData({ params, query, store }) {
    return {
      post: store.getters.postById(Number(params.id)),
      id: params.commentid,
      reply: query["reply"] === undefined ? false : Boolean(query["reply"])
    };
  },
  data() {
    return {
      replies: []
    }
  },
  mounted() {
      this.$axios.get(process.env.AXIOS_URL + "/post/" + this.$route.params.id + "/comments").then((res) => {
        this.replies = res.data[this.id-1].replies
    });
  },
  components: {
    CommentReplies,
    CommentForm
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