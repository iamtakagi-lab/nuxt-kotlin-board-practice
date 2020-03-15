<template>
  <div class="headline">
    <div class="top">
      <p v-if="post.closed" style="color: red; text-align: center;">この募集は締め切られました</p>
      <div class="headline-title">
        {{post.title}}
        <p class="report" @click="openModal">[通報]</p>
      </div>

      <p class="date">投稿日: {{ new Date(post.publishedAt) | format-date }}</p>
      <p class="date" v-show="post.lastEdited">
        更新: {{ new Date(post.lastEdited) | format-date }}
        ({{ new Date(post.lastEdited) | from-now}})
      </p>
      <p class="date" v-show="post.lastBumped">
        順位更新: {{ new Date(post.lastBumped) | format-date }}
        ({{ new Date(post.lastBumped) | from-now}})
      </p>
    </div>

    <p class="username">投稿者: {{ post.username }}</p>
    <p class="fc">フレンドコード: {{post.fc}}</p>

    <div class="tags" v-if="post.tags.length">
      <p style="display: inline-block;">タグ:</p>
      <nuxt-link
        v-for="(tag, i) in post.tags.split(',')"
        :key="i"
        class="tag"
        style="
           position: relative;
           display: inline-block;
           margin: 4px;"
        :to="linkTo('tag', tag)"
      >
        <font-awesome-icon icon="tags" style="font-size: 13px" />
        {{tag}}
      </nuxt-link>
    </div>

     <Modal @close="closeModal" v-if="modal">
      <!-- default スロットコンテンツ -->
      <p style="font-size: 18px">投稿を通報する</p>
      <p style="font-size: 15px">※複数回、通報の送信をした場合はアクセス禁止措置を取らせていただきます。</p>

      <!-- /default -->
      <!-- footer スロットコンテンツ -->
      <template slot="footer">
        <button v-if="!submitted" @click="doSend">送信</button>
        <p v-else-if="submitted">送信中...</p>
      </template>
      <!-- /footer -->
    </Modal>
  </div>
</template>

<script>
import ShareButtons from "~/components/post/ShareButtons";
import Modal from "~/components/modal/Modal";
import { mapState, mapGetters } from "vuex";
export default {
  name: "Headline",
  props: ["post"],
  computed: {
    ...mapGetters(["linkTo"])
  },
  components: {
    ShareButtons,
    Modal
  },
  data() {
    return {
      modal: false,
      submitted: false,
    }
  },
  methods: {
    openModal() {
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
    async doSend() {
      const report = {
        type: "post",
        path: "post/" + this.post.id,
        ip:  this.$store.state.address
      };
      this.submitted = true;
      await this.$axios
        .post(process.env.AXIOS_URL + "/report", report)
        .then(async res => {
          this.submitted = false
          this.closeModal();
        });
    }
  }
};
</script>

<style scoped lang="scss">
.img {
  margin-top: 20px;
  height: auto;
  max-width: 100%;
  vertical-align: middle;
  position: relative;
}

.headline {
  margin-top: 10px;
  margin-left: 1em;
  //top: 10px;
  //left: 10px;

  .tags {
    margin-top: 10px;
  }

  .username {
    display: inline-flex;
    margin-top: 10px;
    color: grey;
    font-size: 15px;

    @media screen and (max-width: 768px) {
      font-size: 12px;
    }
  }

  .top {
    .headline-title {
      font-size: 30px;
      text-decoration: none !important;

      @media screen and (max-width: 768px) {
        font-size: 18px;
      }

      &:hover {
        text-decoration: none !important;
      }

      .report {
        font-size: 15px;
        display: inline-block;
        float: right;
        cursor: pointer;
        margin-right: 1em;
      }
    }
    .date {
      display: inline-flex;
      margin-top: 10px;
      color: grey;
      font-size: 15px;

      @media screen and (max-width: 768px) {
        font-size: 12px;
      }
    }
  }
}
</style>
