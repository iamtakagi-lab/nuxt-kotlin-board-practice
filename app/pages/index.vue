<template>
  <div>
    <Hero/>

    <section class="index-tags">
       <div  class="label" style="background: #2581dc;">
        <div class="label-text">人気のタグ</div>
      </div>
      <div class="tags">
         <nuxt-link
        v-for="(tag, i) in tags.slice(0, 30)"
        :key="i"
        class="tag"
        style="
           position: relative;
           display: inline-block;
           margin: 2px;"
        :to="linkTo('tag', tag.name)"
      >
        <font-awesome-icon icon="tags" style="font-size: 13px" />
        {{tag.name}} ({{tag.count}})
      </nuxt-link>
      </div>
     
    </section>

    <section class="latest-posts">
      <div class="underlined-title" style="font-size: 35px; margin-top: 10px;">募集一覧</div>

      <div class="flex">
        <PostCard
          v-for="(post, i) in $store.state.allPosts.slice(getStart, getCurrent)"
          :key="i"
          :post="post"
        />
      </div>

      <div>
        <nuxt-link
          v-show="hasPrev"
          class="button"
          :to="`/?page=${getPrev}`"
          @click.native="clickCallback(getPrev)"
        >前のページ</nuxt-link>

        <nuxt-link
          v-show="!hasPrev"
          class="button"
          :class="{ 'is-disabled': true }"
          :to="`/?page=${getPrev}`"
          @click.native="clickCallback(getPrev)"
        >前のページ</nuxt-link>

        <p
          style="display: inline-flex;"
        >{{currentPage}} / {{Math.ceil($store.state.allPosts.length / this.parPage)}}</p>

        <nuxt-link
          v-show="(this.currentPage < Math.ceil($store.state.allPosts.length / this.parPage))"
          class="button"
          :to="`/?page=${getNext}`"
          @click.native="clickCallback(getNext)"
        >次のページ</nuxt-link>

        <nuxt-link
          v-show="!(this.currentPage < Math.ceil($store.state.allPosts.length / this.parPage))"
          class="button"
          :class="{ 'is-disabled': true }"
          :to="`/?page=${getNext}`"
          @click.native="clickCallback(getNext)"
        >次のページ</nuxt-link>
      </div>
    </section>

    <PostButton />
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
    ...mapState(["allPosts"]),
    ...mapState(["tags"]),
    ...mapGetters(["linkTo"]),

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
      this.$router.push(`/?page=${page}`);
    }
  },
  async asyncData({ params, query, error }) {
    return {
      tag: params.id,
      currentPage: query["page"] === undefined ? 1 : Number(query["page"])
    };
  }
};
</script>

<style>
</style>
