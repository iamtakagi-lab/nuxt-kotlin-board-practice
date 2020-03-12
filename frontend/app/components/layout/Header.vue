<template>
  <section class="navigation">
    <div class="nav-container">
      <nuxt-link to="/" class="brand no-decoration no-selection">あつ森.space</nuxt-link>

      <nav>
        <div class="nav-mobile">
          <a id="nav-toggle">
            <span></span>
          </a>
        </div>

        <ul class="nav-list">
          <li v-bind:class="{'active': this.$route.path.includes('/posts/trade')}">
            <nuxt-link to="/posts/trade">アイテム交換募集</nuxt-link>
          </li>
          <li v-bind:class="{'active': this.$route.path.includes('/posts/friend')}">
            <nuxt-link to="/posts/friend">フレンド募集</nuxt-link>
          </li>
        </ul>
      </nav>
    </div>
  </section>
</template>

<script>
import $ from "jquery";

export default {
  name: "Header",
  mounted() {
    // If a link has a dropdown, add sub menu toggle.
    $("nav ul li a:not(:only-child)").click(function(e) {
      $(this)
        .siblings(".nav-dropdown")
        .toggle();
      // Close one dropdown when selecting another
      $(".nav-dropdown")
        .not($(this).siblings())
        .hide();
      e.stopPropagation();
    });
    // Clicking away from dropdown will remove the dropdown class
    $("html").click(function() {
      $(".nav-dropdown").hide();
    });
    // Toggle open and close nav styles on click
    $("#nav-toggle").click(function() {
      $("nav ul").slideToggle();
    });
    // Hamburger to X toggle
     $("#nav-toggle").on("click", function() {
      this.classList.toggle("active");
    });
  }
};
</script>

<style scoped lang = "scss">
// Navigation Variables
$content-width: 1000px;
$breakpoint: 768px;
$nav-height: 60px;
$nav-mobile-height: 50px;
//$nav-background: #4721c4;
$nav-background: royalblue;
$nav-font-color: #ffffff;
$link-hover-color: #2581dc;

// Outer navigation wrapper
.navigation {
  height: $nav-height;
  background: $nav-background;
  position: sticky;
  top: 0;
  z-index: 10000;

  @media screen and (max-width: 768px) {
    height: $nav-mobile-height;
  }
}

// Logo and branding
.brand {
  position: absolute;
  float: left;
  padding-left: 20px;
  line-height: $nav-height;
  font-size: 1.4em;
  color: $nav-font-color;

  @media screen and (max-width: 768px) {
    line-height: $nav-mobile-height;
    margin-left: auto;
    margin-right: auto;
  }
}

// Container with no padding for navbar
.nav-container {
  max-width: $content-width;
  margin: 0 auto;
}

// Navigation
nav {
  float: right;
  display: block;
  ul {
    list-style: none;
    margin: 0;
    padding: 0;
    li {
      float: left;
      position: relative;
      a {
        position: relative;
        display: inline-block;
        text-decoration: none;
        color: $nav-font-color;
        display: block;
        padding: 0 10px;
        line-height: $nav-height;
        background: $nav-background;
      }
      ul li {
        min-width: 190px;
        a {
          padding: 15px;
          line-height: 20px;
        }
      }
    }
  }
}

// Dropdown list binds to JS toggle event
.nav-dropdown {
  position: absolute;
  display: none;
  z-index: 1;
  box-shadow: 0 3px 12px rgba(0, 0, 0, 0.15);
}

/* Mobile navigation */

// Binds to JS Toggle
.nav-mobile {
  display: none;
  position: absolute;
  top: 0;
  right: 0;
  background: $nav-background;
  height: $nav-mobile-height;
  width: $nav-mobile-height;
}
@media only screen and (max-width: 768px) {
  // Hamburger nav visible on mobile only
  .nav-mobile {
    display: block;
  }
  nav {
    width: 100%;
    padding: $nav-height 0 0;

    @media screen and (max-width: 768px) {
      padding: $nav-mobile-height 0 0;
    }
    ul {
      display: none;
      li {
        float: none;
        a {
          padding: 15px;
          line-height: 20px;
        }
        ul li a {
          padding-left: 30px;
        }
      }
    }
  }
  .nav-dropdown {
    position: static;
  }
}
@media screen and (min-width: 768px) {
  .nav-list {
    display: block !important;
  }
}
#nav-toggle {
  position: absolute;
  left: 5px;
  top: 12px;
  cursor: pointer;
  padding: 10px 35px 16px 0px;
  span,
  span:before,
  span:after {
    cursor: pointer;
    border-radius: 5px;
    height: 5px;
    width: 35px;
    background: $nav-font-color;
    position: absolute;
    display: block;
    content: "";
    transition: all 300ms ease-in-out;
  }
  span:before {
    top: -11px;
  }
  span:after {
    bottom: -11px;
  }
  &.active span {
    background-color: transparent;
    &:before,
    &:after {
      top: 0;
    }
    &:before {
      transform: rotate(45deg);
    }
    &:after {
      transform: rotate(-45deg);
    }
  }
}
</style>
