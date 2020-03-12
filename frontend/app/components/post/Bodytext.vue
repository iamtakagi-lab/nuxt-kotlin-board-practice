<template>
  <div class="markdown-body" v-html="$md.render(post.body)"></div>

</template>

<script>
    export default {
        name: "Bodytext",
        props: ["post"]
    }
</script>

<style scoped lang="scss">


</style>
