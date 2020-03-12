<template>
  <footer>
    <div class="footer-content">
      <p>
        &copy; 2020
        <nuxt-link to="/" class="no-decoration no-selection" style="color: #777;">あつ森.space</nuxt-link>
      </p>
      <p style="display: inline-block;">
        <nuxt-link
          to="/privacy-policy"
          class="no-decoration no-selection"
          style="color: #777;"
        >プライバシーポリシー</nuxt-link>
      </p>
      <p style="margin-left: 10px; display: inline-block;">
        <nuxt-link
          to="/terms-of-service"
          class="no-decoration no-selection"
          style="color: #777;"
        >利用規約</nuxt-link>
      </p>
    </div>
  </footer>
</template>

<script>
export default {};
</script>

<style lang="scss" scoped>
footer {
  width: 100%;
  position: absolute;
  text-align: center;
  z-index: -1;

  .footer-content {
    padding: 50px;
    cursor: pointer;
    color: #777;
    font-size: 15px;
  }
}
</style>