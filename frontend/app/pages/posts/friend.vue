<template>
  <div>

    <section class="latest-posts" style="margin-top: 10px">

      <div class="flex">
        <PostCard v-for="(post, i) in $store.state.friendPosts" :key="i" :post="post"/> 
      </div>

       <div>
        <nuxt-link
          v-show="hasPrev"
          class="button"
          :to="`/posts/friend?page=${getPrev}`"
          @click.native="clickCallback(getPrev)"
        >前のページ</nuxt-link>

        <button
          v-show="!hasPrev"
          class="button"
          :class="{ 'is-disabled': true }"
        >前のページ</button>

        <p
          style="display: inline-flex;"
        >{{currentPage}} / {{Math.ceil($store.state.friendPosts.length / this.parPage)}}</p>

        <nuxt-link
          v-show="(this.currentPage < Math.ceil($store.state.friendPosts.length / this.parPage))"
          class="button"
          :to="`/posts/friend?page=${getNext}`"
          @click.native="clickCallback(getNext)"
        >次のページ</nuxt-link>

        <button
          v-show="!(this.currentPage < Math.ceil($store.state.friendPosts.length / this.parPage))"
          class="button"
          :class="{ 'is-disabled': true }"
        >次のページ</button>
      </div>

    </section>

    <PostButton/>

  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import PostCard from "~/components/post/PostCard";
import Hero from "~/components/layout/Hero";
import PostButton from "~/components/layout/PostButton";
export default {
  components: {
    Hero,
    PostCard,
    PostButton
  },
  computed: {
    ...mapState(["friendPosts"]),

    getCurrent: function() {
      return this.currentPage * this.parPage;
    },

    getStart: function() {
      let current = this.currentPage * this.parPage;
      return current - this.parPage;
    },

    getPrev: function() {
      return this.currentPage - 1;
    },

    getNext: function() {
      return this.currentPage + 1;
    },

    hasPrev: function() {
      return this.currentPage > 1;
    }
  },
  data() {
    return {
      parPage: 16
    };
  },
  methods: {
    clickCallback (page) {
      this.currentPage = Number(page);
      this.$router.push(`/posts/friend?page=${page}`);
    }
  },
  async asyncData({ params, query, error }) {
    return {
      tag: params.id,
      currentPage: query["page"] === undefined ? 1 : Number(query["page"])
    };
  },
  head() {
     return {
      title: "フレンド募集 - あつ森.space",
     }
  }
};
</script>

<style>
</style>
