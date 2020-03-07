<template>
  <div>
    <Header/>

    <main>
      <transition name="page">
        <nuxt/>
      </transition>
    </main>

    <Footer/>
  </div>
</template>

<script>
import Header from "~/components/layout/Header";
import Footer from "~/components/layout/Footer";
export default {
  components: {
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
