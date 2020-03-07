<template>
  <section class="post-preview">
    <nuxt-link class="no-decoration no-selection" :to="linkTo('post', post.id)">
      <div
        v-if="post.type === 'trade'"
        class="label no-decoration no-selection"
        style="background: #2581dc;"
      >
        <h2 class="title no-decoration no-selection">{{ post.title }}</h2>
      </div>
      <div
        v-else-if="post.type === 'friend'"
        class="label no-decoration no-selection"
        style="background: tomato;"
      >
        <h2 class="title no-decoration no-selection">{{ post.title }}</h2>
      </div>
      <div class="text-content">
        <p v-if="plainBody" class="description">
          {{
          plainBody.length >= 50 ?
           plainBody.substring(0, 50) + "..." : plainBody}}
        </p>
         <p v-if="post.closed" style="color: red;">Closed</p>
      </div>
      <p class="date">{{ new Date(post.publishedAt) | format-date }}</p>
    </nuxt-link>
  </section>
</template>
    
<script>
import { mapState, mapGetters } from "vuex";
import remark from "remark";
import strip from "strip-markdown";
export default {
  props: ["post"],
  computed: {
    ...mapGetters(["linkTo"])
  },
  data() {
    return {
      plainBody: null
    };
  },
  mounted() {
    let self = this;
    remark()
      .use(strip)
      .process(this.post.body, function(err, file) {
        if (err) throw err;
        self.plainBody = file.toString();
      });
  }
};
</script>

<style lang="scss">
</style>