<template>
    <nuxt-link :to="`/post/${post.id}/edit`" class="float-circle" style="background: royalblue;">
      <font-awesome-icon icon="edit" style="font-size: 50px; color: white; margin: 23px;"/>
    </nuxt-link>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  props: ["post"],
  computed: {
    ...mapGetters(["linkTo"])
  },
}
</script>