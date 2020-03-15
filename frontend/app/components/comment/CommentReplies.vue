<template>
  <section class="wrapper">
    <h1 class="title">>>{{id}} への返信</h1>
    <div class="comments">
        <Comment :comment="comment" :post="post" :reply="false"/>
    </div>
    <div v-if="comment.replies.length" class="comments">
      <Comment v-for="(reply, i) in comment.replies" :key="i" :comment="reply" :post="post" :reply="true"/>
    </div>
  </section>
</template>

<script>
import Comment from "~/components/comment/Comment";
import $ from "jquery";
export default {
  components: { Comment },
  props: ["id", "comment", "post"]
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