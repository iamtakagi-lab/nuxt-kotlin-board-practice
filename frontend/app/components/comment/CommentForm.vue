<template>
  <section class="comment-form">
    <form @submit.prevent="sendComment">
      <div class="label" style="background: #444;">
        <div class="label-text">コメント入力</div>
      </div>

      <div class="field">
        <input
          type="text"
          name="username"
          id="username"
          placeholder="名前"
          v-model="commentform.username"
          required
          @change="validate"
        />
        <label for="username" style=" margin-top: 10px;">名前</label>
      </div>
      <p class="error" v-if="errors.username">{{errors.username}}</p>

      <div class="field">
        <textarea
          style="margin-bottom: 10px"
          type="textarea"
          name="context"
          id="context"
          placeholder="コメント"
          v-model="commentform.context"
          required
          @change="validate"
        />
      </div>
      <p>{{commentform.context.length}} / {{maxContextLength}}</p>
      <p class="error" v-if="errors.context">{{errors.context}}</p>
      <p v-if="submitted">送信中...</p>

      <button v-if="!submitted && post.comments.length < 500" class="submit-button" id="submit" style="background: #444;" type="submit">コメントする</button>
      <p v-else-if="post.comments.length > 500">コメントが500件を超えているため送信することはできません</p>
    </form>

    <Footerline :post="post"/>
  </section>
</template>

<script>
import $ from "jquery";
import Footerline from "~/components/post/Footerline";
export default {
  props: {
    post: {
      required: true
    },
    reply: {
      required: false
    }
  },
  mounted() {
    if(this.reply){
       this.commentform.context = ">>" + this.reply
    }
  },
  components: { Footerline },
  methods: {
    validate() {
      this.validated = false;

      this.errors.username = null;
      this.errors.context = null;

      if (this.commentform.username.length <= 0) {
        this.errors.username = "名前を入力してください";
      }
      if (this.commentform.username.length > 20) {
        this.errors.username = "名前を20文字以下にしてください";
      }

      if (this.commentform.context.length <= 0) {
        this.errors.body = "コメントを入力してください";
      }

      if (this.commentform.context.length > this.maxContextLength) {
        this.errors.context = `内容を${this.maxContextLength}文字以下にしてください`;
      }

      if (this.errors.username === null && this.errors.context === null) {
        this.validated = true;
      }
    },
    async sendComment() {
      if (!this.submitted) {
        this.validate();
        if (this.validated) {
          this.submitted = true;
          await this.$axios
            .post(process.env.AXIOS_URL + "/post/" + this.post.id + "/comment", this.commentform)
            .then(async res => {
              await this.$store.dispatch("getPosts");
              setTimeout("location.reload()", 1000);
            });
        }
      }
    }
  },
  data() {
    return {
      maxContextLength: 150,
      validated: false,
      submitted: false,
      errors: {
        username: null,
        context: null
      },
      commentform: {
        username: "匿名",
        context: "",
        ip: this.$store.state.address
      }
    };
  }
};
</script>

<style lang="scss" scoped>
.comment-form {
  margin-top: 20px;
  margin-bottom: 50px;
  background: white;
  max-width: 768px;
  border-radius: 10px;

  .label {
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
    text-align: center;
    background: #2581dc;
    text-decoration: none !important;
    margin: 0 auto;
    transition: 0.4s;
    color: white;
    user-select: none; /* CSS3 */
    -moz-user-select: none; /* Firefox */
    -webkit-user-select: none; /* Safari、Chromeなど */
    -ms-user-select: none; /* IE10かららしい */
    max-width: 100%;
    height: 50px;

    .label-text {
      line-height: 50px;
      font-size: 20px;
      margin: 30px;
    }
  }
}
</style>