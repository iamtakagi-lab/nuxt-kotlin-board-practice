<template>
  <div v-if="!post.closed">
    <section v-if="!preview && !closeConfirm && !bumpConfirm" class="post-form">
      <div v-if="post.type === 'trade'" class="label" style="background: #2581dc;">
        <div class="label-text">アイテム交換募集を編集する</div>
      </div>

      <div v-else-if="post.type === 'friend'" class="label" style="background: tomato;">
        <div class="label-text">フレンド募集を編集する</div>
      </div>

      <form @submit.prevent="submitEdit">
        <div class="field">
          <input
            type="text"
            name="title"
            id="title"
            placeholder="タイトル"
            v-model="postform.title"
            required
            @change="validate"
            maxlength="20"
          />
          <label for="title">タイトル ({{postform.title.length}}/{{this.maxTitleLength}})</label>
        </div>
        <p class="error" v-if="errors.title">{{errors.title}}</p>

        <div class="field">
          <input
            type="text"
            name="username"
            id="username"
            placeholder="名前"
            v-model="postform.username"
            required
            @change="validate"
            maxlength="20"
          />
          <label for="username">名前 ({{postform.username.length}}/{{this.maxUsernameLength}})</label>
        </div>
        <p class="error" v-if="errors.username">{{errors.username}}</p>

        <div class="field">
          <input
            type="text"
            name="fc"
            id="fc"
            placeholder="0000-0000-0000"
            v-model="postform.fc"
            required
            @change="validate"
            maxlength="14"
          />
          <label for="fc">フレンドコード</label>
        </div>
        <p class="error" v-if="errors.fc">{{errors.fc}}</p>

        <div class="field">
          <input
            type="text"
            name="tags"
            id="tags"
            placeholder="複数の場合は「,」で区切ってください"
            v-model="postform.tags"
            maxlength="100"
            @change="validate"
          />
          <label for="tags">タグ ({{postform.tags.length}}/{{this.maxTagsLength}})</label>
        </div>
        <p class="error" v-if="errors.tags">{{errors.tags}}</p>

        <div class="mavonEditor">
          <no-ssr>
            <mavon-editor
              :toolbars="markdownOption"
              :language="'ja'"
              v-model="body"
              :boxShadow="false"
            />
          </no-ssr>
        </div>
        <p>{{body.length}} / {{maxBodyLength}}</p>
        <p class="error" v-if="errors.body">{{errors.body}}</p>

        <button
          class="submit-button"
          style="background: #552CCD; margin-top: 15px;"
          type="button"
          @click="handleBumpConfirm"
        >表示順位を上げる</button>

        <button
          class="submit-button"
          style="background: crimson; margin-top: 15px;"
          type="button"
          @click="handleCloseConfirm"
        >募集を締め切る</button>

        <button
          class="submit-button"
          style="background: forestgreen; margin-top: 15px;"
          type="submit"
        >編集を適用する</button>
      </form>
    </section>

    <section v-if="preview && !closeConfirm" class="post-form">
      <div class="wrapper">
        <div v-if="post.type === 'trade'" class="label" style="background: #2581dc;">
          <div class="label-text">アイテム交換募集の編集を確定する</div>
        </div>

        <div v-else-if="post.type === 'friend'" class="label" style="background: tomato;">
          <div class="label-text">フレンド募集の編集を確定する</div>
        </div>

        <PreviewHeadline :post="postData" />
        <Bodytext :post="postData" />

        <form @submit.prevent="editPost()">
          <div class="field">
            <input
              type="password"
              name="password"
              placeholder="編集用パスワードを入力"
              v-model="postform.password"
              required
              id="password"
              @change="validatePassword"
            />
            <label for="password">編集用パスワードを入力</label>
          </div>
          <p class="error" v-if="errors.password">{{errors.password}}</p>

          <button
            class="submit-button"
            style="background: crimson; margin-top: 15px;"
            @click="cancel"
          >編集画面に戻る</button>
          <button
            class="submit-button"
            style="background: forestgreen; margin-top: 15px;"
            type="submit"
          >編集を確定する</button>
        </form>
      </div>
    </section>

    <section v-else-if="bumpConfirm" class="post-form">
      <div class="wrapper">
        <div class="label" style="background: #552CCD;">
          <div class="label-text">表示順位を上げる</div>
        </div>
        <div v-if="!(new Date().getTime() >= this.post.lastBumped + 1000 * 60 * 30)">
          <div
            style="margin-left: 20px; margin-right: 20px; margin-top: 10px;"
          >表示順位を上げられるようになるまで: {{ new Date(bumpTimeLeft) | countdown }}</div>
        </div>
        <form @submit.prevent="bumpPost">
          <div class="field">
            <input
              type="password"
              name="password"
              placeholder="編集用パスワードを入力"
              v-model="postform.password"
              required
              id="password"
              @change="validatePassword"
            />
            <label for="password">編集用パスワードを入力</label>
          </div>
          <p class="error" v-if="errors.password">{{errors.password}}</p>

          <button
            class="submit-button"
            style="background: crimson; margin-top: 15px;"
            @click="cancel"
          >編集画面に戻る</button>
          <button
            class="submit-button"
            style="background: forestgreen; margin-top: 15px;"
            type="submit"
          >表示順位を上げる</button>

          <p class="error" v-if="errors.bump">{{errors.bump}}</p>
        </form>
      </div>
    </section>

    <section v-else-if="closeConfirm" class="post-form">
      <div class="wrapper">
        <div class="label" style="background: orange;">
          <div class="label-text">募集を締め切りますか？</div>
        </div>
        <form @submit.prevent="closePost()">
          <div class="field">
            <input
              type="password"
              name="password"
              placeholder="編集用パスワードを入力"
              v-model="postform.password"
              required
              id="password"
              @change="validatePassword"
            />
            <label for="password">編集用パスワードを入力</label>
          </div>
          <p class="error" v-if="errors.password">{{errors.password}}</p>

          <button
            class="submit-button"
            style="background: forestgreen; margin-top: 15px;"
            @click="cancel"
          >編集画面に戻る</button>
          <button
            class="submit-button"
            style="background: crimson; margin-top: 15px;"
            type="submit"
          >締め切りを確定する</button>
        </form>
      </div>
    </section>
  </div>
</template>

<script>
import $ from "jquery";
import PreviewHeadline from "~/components/post/PreviewHeadline";
import Bodytext from "~/components/post/Bodytext";
export default {
  mounted() {
    //入力時に郵便番号に自動でハイフンを付けるイベント
    $("#fc").on("keyup", function(e) {
      var input = $(this).val();

      //削除キーではハイフン追加処理が働かないように制御（8がBackspace、46がDelete)
      var key = event.keyCode || event.charCode;
      if (key == 8 || key == 46) {
        return false;
      }

      //4桁目に値が入ったら発動
      if (input.length === 4) {
        $(this).val(input.slice(0, 4) + "-" + input.slice(4, input.length));
      }

      //9桁目に値が入ったら発動
      if (input.length === 9) {
        $(this).val(input.slice(0, 9) + "-" + input.slice(9, input.length));
      }
    });
  },
  components: {
    PreviewHeadline,
    Bodytext
  },
  async asyncData({ params, store }) {
    return {
      post: store.getters.postById(Number(params.id))
    };
  },
  mounted() {
    this.postform.title = this.post.title;
    this.postform.username = this.post.username;
    this.postform.fc = this.post.fc;
    this.postform.tags = this.post.tags;
    this.body = this.post.body;
    this.postform.type = this.post.type;

    if (!(new Date().getTime() >= this.post.lastBumped + 1000 * 60 * 30)) {
      this.bumpTimeLeft =
        this.post.lastBumped + 1000 * 60 * 30 - new Date().getTime();
    }
  },
  head() {
    return {
      title: "投稿編集 | あつ森ちゃんねる"
    };
  },
  middleware: "getPosts",
  methods: {
    handleCloseConfirm() {
      this.validate();
      if (this.validated) {
        this.preview = false;
        this.closeConfirm = true;
      }
    },
    countBump: function() {
      if (!(new Date().getTime() >= this.post.lastBumped + 1000 * 60 * 30)) {
        this.bumpTimeLeft =
          this.post.lastBumped + 1000 * 60 * 30 - new Date().getTime();
      }
    },
    handleBumpConfirm: function() {
      this.bumpConfirm = true;
      const self = this;
      this.timer = setInterval(function() {
        self.countBump();
      }, 1000);
    },
    handleCloseConfirm() {
      this.validate();
      if (this.validated) {
        this.preview = false;
        this.closeConfirm = true;
      }
    },
    cancel() {
      this.bumpConfirm = false;
      this.preview = false;
      this.closeConfirm = false;
    },
    validatePassword() {
      this.validatedPassword = false;
      this.errors.password = null;

      if (this.postform.password.length <= 0) {
        this.errors.password = "編集用パスワードを入力してください";
      }

      if (this.postform.password !== this.post.password) {
        this.errors.password = "正しい編集用パスワードを入力してください";
      }

      if (this.errors.password === null) {
        this.validatedPassword = true;
      }
    },
    validate() {
      this.validated = false;
      this.errors.title = null;
      this.errors.username = null;
      this.errors.fc = null;
      this.errors.body = null;
      this.errors.tags = null;
      this.errors.bump = null;

      if (this.postform.title.length <= 0) {
        this.errors.title = "タイトルを入力してください";
      }
      if (this.postform.title.length > this.maxTitleLength) {
        this.errors.title = `タイトルを${this.maxTitleLength}文字以下にしてください`;
      }

      if (this.postform.username.length <= 0) {
        this.errors.username = "名前を入力してください";
      }
      if (this.postform.username.length > this.maxUsernameLength) {
        this.errors.username = `名前を${this.maxUsernameLength}文字以下にしてください`;
      }

      if (this.postform.tags.length > this.maxTagsLength) {
        this.errors.tags = `タグを${this.maxTagsLength}文字以下にしてください`;
      }

      if (this.postform.fc.length <= 0) {
        this.errors.fc = "フレンドコードを入力してください";
      }

      //5桁目と10桁目が'-(ハイフン)’かどうかをチェック
      if (
        this.postform.fc.length < 14 ||
        (this.postform.fc.length >= 4 &&
          this.postform.fc.substr(4, 1) !== "-") ||
        (this.postform.fc.length >= 9 &&
          this.postform.fc.substr(9, 1) !== "-") ||
        !this.postform.fc.match(/^[0-9\-]+$/)
      ) {
        this.errors.fc = "フレンドコードの形式が正しくありません";
      }

      if (this.body.length <= 0) {
        this.errors.body = "内容を入力してください";
      }

      if (this.body.length > this.maxBodyLength) {
        this.errors.body = `内容を${this.maxBodyLength}文字以下にしてください`;
      }

      if (!(new Date().getTime() >= this.post.lastBumped + 1000 * 60 * 30)) {
        this.errors.bump = "表示順位を上げられるようになるまでお待ちください";
      }

      if (
        this.errors.title === null &&
        this.errors.username === null &&
        this.errors.fc === null &&
        this.errors.body === null &&
        this.errors.tags === null &&
        this.errors.bump === null
      ) {
        this.validated = true;
      }
    },
    submitEdit() {
      this.validate();
      if (this.validated) {
        this.postData = {
          id: this.post.id,
          username: this.postform.username,
          fc: this.postform.fc,
          title: this.postform.title,
          body: this.body,
          tags: this.postform.tags,
          ip: this.$store.state.address
        };
        this.preview = true;
        this.deleteConfirm = false;
      }
    },
    async editPost() {
      this.validatePassword();
      if (this.validatedPassword) {
        await this.$axios
          .put(process.env.AXIOS_URL + "/post/edit", this.postData)
          .then(async res => {
            await this.$store.dispatch("getPosts");
            this.$router.push(this.$store.getters.linkTo("post", this.post.id));
          });
      }
    },
    async closePost() {
      this.validatePassword();
      if (this.validatedPassword) {
        await this.$axios
          .delete(process.env.AXIOS_URL + "/post/" + this.post.id)
          .then(async res => {
            await this.$store.dispatch("getPosts");
            this.$router.push(this.$store.getters.linkTo("post", this.post.id));
          });
      }
    },
    async bumpPost() {
      this.validate();
      if (this.validated) {
        this.preview = false;
        await this.$axios
          .post(process.env.AXIOS_URL + "/post/bump/" + this.post.id)
          .then(async res => {
            await this.$store.dispatch("getPosts");
            this.$router.push(this.$store.getters.linkTo("post", this.post.id));
          });
      }
    }
  },
  data() {
    return {
      maxUsernameLength: 20,
      maxTitleLength: 20,
      maxTagsLength: 100,
      maxBodyLength: 800,
      sentPost: false,
      postData: null,
      closeConfirm: false,
      preview: false,
      validated: false,
      validatedPassword: false,
      bumpConfirm: false,
      bumpTimeLeft: 0,
      timer: null,
      errors: {
        title: null,
        username: null,
        fc: null,
        password: null,
        body: null,
        tags: null,
        bump: null
      },
      postform: {
        title: "",
        username: "",
        fc: "",
        password: "",
        type: "",
        tags: ""
      },
      markdownOption: {
        bold: true,
        italic: true,
        header: true,
        underline: true,
        strikethrough: true,
        mark: true,
        superscript: true,
        subscript: true,
        quote: true,
        ol: true,
        ul: true,
        link: true,
        code: true,
        table: true,
        fullscreen: true,
        readmodel: true,
        htmlcode: true,
        help: true,
        fullscreen: false
      },
      body: ""
    };
  }
};
</script>

<style scoped lang = "scss">
.mavonEditor {
  width: 100%;
  height: 100%;
}

.post-form {
  margin-top: 20px;
  margin-bottom: 50px;
  background: white;
  max-width: 768px;
  margin: 0 auto;
  border-radius: 10px;

  @media screen and (max-width: 768px) {
    margin-right: 10px;
    margin-left: 10px;
  }

  .error {
    font-size: 12px;
    color: red;
    margin-left: 20px;
  }

  .label {
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
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;

    .label-text {
      line-height: 50px;
      font-size: 20px;
      margin: 30px;
    }
  }
}

.wrapper {
  margin-top: 20px;
  margin-bottom: 50px;
  background: white;
  max-width: 768px;
  margin: 0 auto;

  .label {
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