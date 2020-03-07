<template>
  <div>
    <Post :post="post"/>

    <Comment :post="post"/>

    <PostEditButton v-if="!post.closed" :post="post"/>
  </div>
</template>

<script>
import Post from "~/components/post";
import Comment from "~/components/comment";
import PostEditButton from "~/components/layout/PostEditButton";
export default {
  middleware: "getPosts",
  components: {
    Post,
    Comment,
    PostEditButton
  },
  async asyncData({ params, store }) {
    return {
      post: store.getters.postById(Number(params.id))
    };
  }
};
</script>

<style lang="scss" scoped>

</style>