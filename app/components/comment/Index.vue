<template>
  <section class="wrapper">

    <Comments :post="post"/>

    <CommentForm v-if="!post.closed" :post="post"/>
  
  </section>
</template>

<script>
import Comments from "~/components/comment/Comments"
import CommentForm from "~/components/comment/CommentForm";
import Footerline from "~/components/post/Footerline"
export default {
  props: ["post"],
  components: {
    Comments,
    CommentForm,
    Footerline
  },
  mounted() {
    console.log(this.post.comments)
  }
};
</script>

<style lang="scss" scoped>
.wrapper {
  max-width: 768px;
  margin: 0 auto;

  .comments-wrapper {
    margin-top: 20px;
    margin-bottom: 100px;
    background: white;

    .comments {

    }
  }
 
}
</style>