<template>
  <div class="headline">
    <div class="top">
      <p v-if="post.closed" style="color: red; text-align: center;">この募集は締め切られました</p>
      <div class="headline-title">{{post.title}}</div>
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
      <p style="display: inline-block;">タグ: </p>
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
  margin-left: 10px;
  top: 10px;
  left: 10px;

  .headline-title {
    font-size: 30px;
    text-decoration: none !important;

    @media screen and (max-width: 768px) {
      font-size: 18px;
    }

    &:hover {
      text-decoration: none !important;
    }
  }

  .headline-description {
    color: #6c7077;
    font-size: 14px;
    text-decoration: none !important;
    margin-top: 15px;

    &:hover {
      text-decoration: none !important;
    }
  }

  .tags {
    margin-top: 10px;
  }

  .top {
    position: relative;

    .username {
      display: inline-flex;
      margin-top: 10px;
      color: grey;
      font-size: 15px;

      @media screen and (max-width: 768px) {
        font-size: 12px;
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
