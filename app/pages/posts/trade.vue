<template>
  <div>

    <section class="latest-posts">
      <div class="underlined-title" style="font-size: 35px; margin-top: 10px;">アイテム交換募集</div>

      <div class="flex">
        <PostCard v-for="(post, i) in $store.state.tradePosts" :key="i" :post="post"/> 
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
    ...mapState(["tradePosts"])
  }
};
</script>

<style>
</style>
