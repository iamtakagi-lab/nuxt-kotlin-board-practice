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
import remark from "remark";
import strip from "strip-markdown";
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
  },
  head() {
    let description
    remark()
      .use(strip)
      .process(this.post.body, function(err, file) {
        if (err) throw err;
        description = file.toString();
    });
    return {
      title: this.post.title + " | あつ森ちゃんねる",
      meta: [
        {
          hid: "description",
          name: "description",
          content: description.length >= 90 ?
           description.substring(0, 90) + "..." : description
        },
        { hid: "og:url", property: "og:url", content: "https://atumori.space/" },
        {
          hid: "og:title",
          property: "og:title",
          content: this.post.title + " - あつ森.space"
        },
        { hid: "og:type", property: "og:type", content: "website" },
        {
          hid: "og:description",
          property: "og:description",
          content: description.length >= 90 ?
           description.substring(0, 90) + "..." : description
        },
        {
          hid: "twitter:card",
          name: "twitter:card",
          content: "summary_large_image"
        },
        { hid: "og:site_name", property: "og:site_name", content: "あつ森.space" },
        { hid: "og:locale", property: "og:locale", content: "ja_JP" }
      ]
    };
  }
};
</script>

<style lang="scss" scoped>

</style>