<template>
  <div class="comment">
    <div class="headline">
      <p class="username">{{comment.id}}. {{comment.username}}</p>
      <p class="date">{{ new Date(comment.timestamp) | format-date-seconds}}</p>
      <p class="report" @click="openModal">[通報]</p>
      <nuxt-link
        :to="`/post/${post.id}/${comment.id}?reply=true`"
        class="reply no-decoration no-selection"
      >
        <font-awesome-icon icon="reply" style="font-size: 13px" />返信
      </nuxt-link>
    </div>
    <div :id="`context-${comment.id}`">
      <nuxt-link
        v-if="replyContext != null"
        :to="`/post/${post.id}/${replyTo}?reply=true`"
      >{{replyContext}}</nuxt-link>
      {{comment.context.replace('>>', '').replace(/[0-9]/g, '')}}
    </div>
    <nuxt-link
      v-if="reply && comment.replies && comment.replies.length"
      :to="`/post/${post.id}/${comment.id}?reply=true`"
    >{{comment.replies.length}}件の返信</nuxt-link>


    <Modal @close="closeModal" v-if="modal">
      <!-- default スロットコンテンツ -->
      <p style="font-size: 18px">コメントを通報する</p>
      <p style="font-size: 15px">※複数回、通報の送信をした場合はアクセス禁止措置を取らせていただきます。</p>
      <div class="comment">
        <div class="headline">
          <p class="username">{{comment.id}}. {{comment.username}}</p>
          <p class="date">{{ new Date(comment.timestamp) | format-date-seconds}}</p>
          <div :id="`context-${comment.id}`">
            <nuxt-link
              v-if="replyContext != null"
              :to="`/post/${post.id}/${replyTo}?reply=true`"
            >{{replyContext}}</nuxt-link>
            {{comment.context.replace('>>', '').replace(/[0-9]/g, '')}}
          </div>
        </div>
      </div>
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
import { mapState, mapGetters } from "vuex";
import Modal from "~/components/modal/Modal";
export default {
  components: { Modal },
  props: ["comment", "post", "reply"],
  computed: {
    ...mapGetters(["linkTo"])
  },
  data() {
    return {
      replyContext: null,
      replyTo: null,
      modal: false,
      submitted: false
    };
  },
  mounted() {
    const a = this.comment.context.slice(0, this.comment.context.length);
    if (this.comment.context.includes(">>")) {
      this.replyContext = ">>" + a.split(">")[2].replace(/\D/g, "");
      this.replyTo = a.split(">")[2].replace(/\D/g, "");
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
        type: "comment",
        path: "post/" + this.post.id + "/" + this.comment.id,
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

<style lang="scss" scoped>
.comment {
  transition: 500ms;

  .headline {
    font-size: 14px;

    .username {
      display: inline-block;
    }

    .date {
      display: inline-block;
      color: grey;
    }

    .report {
      margin-left: 1px;
      display: inline-block;
      cursor: pointer;
    }

    .reply {
      float: right;
      display: inline-block;
      cursor: pointer;
      font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
      font-size: 0.9em;
      text-decoration: none !important;
      padding: 2px 7px;
      border-radius: 10px;
      background: royalblue;
      transition: 0.4s;
      color: white;
      user-select: none; /* CSS3 */
      -moz-user-select: none; /* Firefox */
      -webkit-user-select: none; /* Safari、Chromeなど */
      -ms-user-select: none; /* IE10かららしい */

      @media screen and (max-width: 768px) {
        font-size: 12px;
      }

      &:hover {
        opacity: 0.9;
        text-decoration: none !important;
      }
    }
  }

  #context {
    margin-top: 7px;
  }

  &::before {
    margin-top: 7px;
    margin-bottom: 7px;
    content: "";
    display: block;
    border-bottom: 1px solid lightgrey;
  }
}
</style>