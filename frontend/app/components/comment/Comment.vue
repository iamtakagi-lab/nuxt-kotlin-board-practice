<template>
  <div class="comment">
    <div class="headline">
      <p class="username">{{comment.id}}. {{comment.username}}</p>
      <p
        class="date no-decoration no-selection"
      >{{ new Date(comment.timestamp) | format-date-seconds}}</p>
      <p class="report" @click="alert('test')">
        [通報]
      </p>
      <p class="reply">
         <font-awesome-icon icon="reply" style="font-size: 13px"/>
        返信
      </p>
    </div>
    <p class="context">{{comment.context}}</p>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
export default {
  components: {},
  props: ["comment"],
  computed: {
    ...mapGetters(["linkTo"])
  }
};
</script>

<style lang="scss" scoped>
.comment {
  
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
    }
  }

  .context {
    margin-top: 7px;
  }

  &::before {
    margin-bottom: 7px;
    content: "";
    display: block;
    border-bottom: 1px solid lightgrey;
  }
}
</style>