<template>
  <div>

    <section style="text-align: center">
      <div class="underlined-title" style="font-size: 35px; margin-top: 10px;">タグ: {{tag}} ({{postsByTag(tag).length}}件)</div>

      <div class="flex">
        <PostCard v-for="(post, i) in postsByTag(tag).slice(getStart, getCurrent)" :key="i" :post="post"/> 
      </div>

      <div>
      <nuxt-link
        v-show="hasPrev"
        class="button"
        :to="`/tag/${tag}?page=${getPrev}`"
      @click.native="clickCallback(getPrev, tag)"
      >前のページ</nuxt-link>

      <nuxt-link
        v-show="!hasPrev"
        class="button" 
        :class="{ 'is-disabled': true }"
        :to="`/tag/${tag}?page=${getPrev}`"
      @click.native="clickCallback(getPrev, tag)"
      >前のページ</nuxt-link>

      <p style="display: inline-flex;">
        {{currentPage}} / {{Math.ceil($store.getters.postsByTag(tag).length / this.parPage)}}
      </p> 

      <nuxt-link
        v-show="(this.currentPage < Math.ceil($store.getters.postsByTag(tag).length / this.parPage))"
        class="button"
        :to="`/tag/${tag}?page=${getNext}`"
       @click.native="clickCallback(getNext, tag)"
      >次のページ</nuxt-link>

      <nuxt-link
        v-show="!(this.currentPage < Math.ceil($store.getters.postsByTag(tag).length / this.parPage))"
        class="button"
        :class="{ 'is-disabled': true }"
        :to="`/tag/${tag}?page=${getNext}`"
        @click.native="clickCallback(getNext, tag)"
      >次のページ</nuxt-link>
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
    ...mapGetters(["postsByTag"]),

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
    clickCallback(page, tag) {
      this.currentPage = Number(page);
      this.$router.push(`/tag/${tag}?page=${page}`);
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
      title: "タグ: " + this.tag + " - atumori.space"
    };
  },
};
</script>

<style>
</style>
