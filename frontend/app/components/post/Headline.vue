<template>
  <div class="headline">
    <div class="top">
      <p v-if="post.closed" style="color: red; text-align: center;">この募集は締め切られました</p>
      <div class="headline-title">
        {{post.title}}
        <p class="report">[通報]</p>
      </div>

      <p class="date">投稿日: {{ new Date(post.publishedAt) | format-date }}</p>
      <p class="date" v-show="post.lastEdited">
        更新: {{ new Date(post.lastEdited) | format-date }}
        ({{ new Date(post.lastEdited) | from-now}})
      </p>
      <p class="date" v-show="post.lastBumped">
        順位更新: {{ new Date(post.lastBumped) | format-date }}
        ({{ new Date(post.lastBumped) | from-now}})
      </p>
    </div>

    <p class="username">投稿者: {{ post.username }}</p>
    <p class="fc">フレンドコード: {{post.fc}}</p>

    <div class="tags" v-if="post.tags.length">
      <p style="display: inline-block;">タグ:</p>
      <nuxt-link
        v-for="(tag, i) in post.tags.split(',')"
        :key="i"
        class="tag"
        style="
           position: relative;
           display: inline-block;
           margin: 4px;"
        :to="linkTo('tag', tag)"
      >
        <font-awesome-icon icon="tags" style="font-size: 13px" />
        {{tag}}
      </nuxt-link>
    </div>
  </div>
</template>

<script>
import ShareButtons from "~/components/post/ShareButtons";
import { mapState, mapGetters } from "vuex";
export default {
  name: "Headline",
  props: ["post"],
  computed: {
    ...mapGetters(["linkTo"])
  },
  components: {
    ShareButtons
  }
};
</script>

<style scoped lang="scss">
.img {
  margin-top: 20px;
  height: auto;
  max-width: 100%;
  vertical-align: middle;
  position: relative;
}

.headline {
  margin-top: 10px;
  margin-left: 1em;
  //top: 10px;
  //left: 10px;

  .tags {
    margin-top: 10px;
  }

  .username {
    display: inline-flex;
    margin-top: 10px;
    color: grey;
    font-size: 15px;

    @media screen and (max-width: 768px) {
      font-size: 12px;
    }
  }

  .top {
    .headline-title {
      font-size: 30px;
      text-decoration: none !important;

      @media screen and (max-width: 768px) {
        font-size: 18px;
      }

      &:hover {
        text-decoration: none !important;
      }

      .report {
        font-size: 15px;
        display: inline-block;
        float: right;
        cursor: pointer;
        margin-right: 1em;
      }
    }
    .date {
      display: inline-flex;
      margin-top: 10px;
      color: grey;
      font-size: 15px;

      @media screen and (max-width: 768px) {
        font-size: 12px;
      }
    }
  }
}
</style>
