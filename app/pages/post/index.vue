<template>
  <div>
    <section v-if="!preview" class="post-form">
      <div v-if="postform.type === 'trade'" class="label" style="background: #2581dc;">
        <div class="label-text">アイテム交換を募集する</div>
      </div>

      <div v-else-if="postform.type === 'friend'" class="label" style="background: tomato;">
        <div class="label-text">フレンドを募集する</div>
      </div>

      <form @submit.prevent="submit">
        <div class="field">
          <input
            type="text"
            name="title"
            id="title"
            placeholder="タイトル"
            v-model="postform.title"
            required
            maxlength="20"
            @change="validate"
          />
          <label for="title">タイトル({{postform.title.length}}/{{this.maxTitleLength}})</label>
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
            maxlength="20"
            @change="validate"
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
            @change="validate"
            maxlength="100"
          />
          <label for="tags">タグ ({{postform.tags.length}}/{{this.maxTagsLength}})</label>
        </div>
        <p class="error" v-if="errors.tags">{{errors.tags}}</p>


        <div class="field">
          <input
            type="password"
            name="password"
            placeholder="編集用パスワード"
            v-model="postform.password"
            required
            id="password"
            @change="validate"
          />
          <label for="password">編集用パスワード</label>
        </div>
        <p class="error" v-if="errors.password">{{errors.password}}</p>

        <div class="field">
          <input
            type="password"
            name="password_confirm"
            placeholder="編集用パスワード(確認)"
            v-model="postform.password_confirm"
            required
            id="password_confirm"
            @change="validate"
          />
          <label for="password_confirm">編集用パスワード(確認)</label>
        </div>
        <p class="error" v-if="errors.password_confirm">{{errors.password_confirm}}</p>

        <div style="margin-left: 20px;">
          <div class="md-radio md-radio-inline">
            <input
              id="trade"
              type="radio"
              name="trade"
              value="trade"
              v-model="postform.type"
              checked
            />
            <label for="trade">アイテム交換募集</label>
          </div>

          <div class="md-radio md-radio-inline">
            <input id="friend" type="radio" name="friend" value="friend" v-model="postform.type" />
            <label for="friend">フレンド募集</label>
          </div>
        </div>

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

         <p>
          {{body.length}} / {{maxBodyLength}}
        </p>
        <p class="error" v-if="errors.body">{{errors.body}}</p>

        <button
          v-if="postform.type === 'trade'"
          class="submit-button"
          style="background: #2581dc;"
          type="submit"
        >アイテム交換募集を投稿する</button>
        <button
          v-else-if="postform.type === 'friend'"
          class="submit-button"
          style="background: tomato;"
          type="submit"
        >フレンド募集を投稿する</button>
      </form>
    </section>

    <section v-else-if="preview" class="post-form">
      <div class="wrapper">
        <div v-if="post.type === 'trade'" class="label" style="background: #2581dc;">
          <div class="label-text">この内容でアイテム交換を募集する</div>
        </div>

        <div v-else-if="post.type === 'friend'" class="label" style="background: tomato;">
          <div class="label-text">この内容でフレンドを募集する</div>
        </div>

        <PreviewHeadline :post="post" />
        <Bodytext :post="post" />

         <button
            class="submit-button"
            style="background: crimson; margin-top: 15px;"
            @click="cancelPreview"
          >編集画面に戻る</button>
          <button
            class="submit-button"
            style="background: forestgreen; margin-top: 15px;"
            @click="sendPost"
          >投稿を確定する</button>
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
  middleware: "getPosts",
  methods: {
    cancelPreview() {
      this.preview = false;
    },
    validate() {
      this.validated = false;
      this.errors.title = null;
      this.errors.username = null;
      this.errors.fc = null;
      this.errors.tags = null;
      this.errors.password = null;
      this.errors.password_confirm = null;
      this.errors.type = null;
      this.errors.body = null;

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

      if (this.postform.password.length <= 0) {
        this.errors.password = "編集用パスワードを入力してください";
      }
      if (this.postform.password.length < 4) {
        this.errors.password = "編集用パスワードを4桁以上にしてください";
      }

      if (this.postform.password_confirm.length <= 0) {
        this.errors.password_confirm =
          "編集用パスワード(確認)を入力してください";
      }
      if (this.postform.password !== this.postform.password_confirm) {
        this.errors.password_confirm = "パスワードが一致しません";
      }

      if (this.body.length <= 0) {
        this.errors.body = "内容を入力してください";
      }

      if (this.body.length > this.maxBodyLength) {
        this.errors.body = `内容を${this.maxBodyLength}文字以下にしてください`;
      }

      if (  
        this.errors.title === null &&
        this.errors.username === null &&
        this.errors.fc === null &&
        this.errors.password === null &&
        this.errors.password_confirm === null &&
        this.errors.type === null &&
        this.errors.body === null &&
        this.errors.tags === null
      ) {
        this.validated = true;
      }
    },
    submit: function(e) {
      this.validate();
      if (this.validated) {
        this.post = {
          username: this.postform.username,
          type: this.postform.type,
          fc: this.postform.fc,
          title: this.postform.title,
          body: this.body,
          tags: this.postform.tags,
          password: this.postform.password
        };
        console.log(this.post);
        this.preview = true;
      }
    },
    async sendPost() {
      this.preview = false;
      await this.$axios.post(process.env.AXIOS_URL + '/post', this.post).then( async   (res) => {
        const id = res.data.id
        await this.$store.dispatch('getPosts')
        this.$router.push(this.$store.getters.linkTo('post', id))
      })
    }
  },
  data() {
    return {
      maxUsernameLength: 20,
      maxTitleLength: 20,
      maxTagsLength: 100,
      maxBodyLength: 500,
      sentPost: false,
      post: null,
      preview: false,
      validated: false,
      errors: {
        title: null,
        username: null,
        fc: null,
        tags: null,
        password: null,
        password_confirm: null,
        type: null,
        body: null
      },
      postform: {
        title: "",
        username: "",
        tags: "",
        fc: "",
        password: "",
        password_confirm: "",
        type: "trade",        
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
        help: true
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