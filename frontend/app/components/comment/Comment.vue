<template>
  <div class="comment">
    <div class="headline">
      <p class="username">{{comment.id}}. {{comment.username}}</p>
      <p class="date">{{ new Date(comment.timestamp) | format-date-seconds}}</p>
      <p class="report">[通報]</p>
      <nuxt-link
        :to="`/post/${post.id}/${comment.id}?reply=true`" class="reply no-decoration no-selection">
        <font-awesome-icon icon="reply" style="font-size: 13px" />返信
      </nuxt-link>
    </div>
    <div id="context">
      {{comment.context}}
    </div>
    <nuxt-link v-if="reply && comment.replies && comment.replies.length" :to="`/post/${post.id}/${comment.id}?reply=true`">
      {{comment.replies.length}}件の返信
    </nuxt-link>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import $ from "jquery";
export default {
  components: {},
  props: ["comment", "post", "reply"],
  computed: {
    ...mapGetters(["linkTo"])
  },
  mounted() {
   
  }
};
</script>

<style lang="scss" scoped>
.comment {
  transition: 500ms;

  .headline {
    font-size: 14px;

    .username {
      display: inline-block;
    }

    .date {
      display: inline-block;
      color: grey;
    }

    .report {
      margin-left: 1px;
      display: inline-block;
      cursor: pointer;
    }

    .reply {
      color: black;
      float: right;
      display: inline-block;
      cursor: pointer;
    }
  }

  #context {
    margin-top: 7px;
  }

  &::before {
    margin-top: 7px;
    margin-bottom: 7px;
    content: "";
    display: block;
    border-bottom: 1px solid lightgrey;
  }
}
</style>