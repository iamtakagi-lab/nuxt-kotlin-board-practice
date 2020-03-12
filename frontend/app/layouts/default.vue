<template>
  <div>
    <Header/>

    <main>
      <transition name="page">
        <nuxt/>
      </transition>
    </main>

    <PostButton v-if="this.$route.path === '/' || this.$route.path.includes('posts')"/>

    <Footer/>
  </div>
</template>

<script>
import PostButton from "~/components/layout/PostButton"
import Header from "~/components/layout/Header";
import Footer from "~/components/layout/Footer";
export default {
  middleware: "getAddress",
  components: {
    PostButton,
    Header,
    Footer
  }
};
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  will-change: opacity;
  transition: opacity 225ms cubic-bezier(0.4, 0, 0.2, 1) 0ms;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.page-enter {
   opacity: 0;
 }
 .page-enter-active {
   transition: opacity 1s;
 }
</style>
