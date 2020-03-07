<template>
  <div class="balloon">
    <div class="faceicon">
      <img src="~/assets/img/png/user.png"/>
    </div>
    <div class="chatting">
      <p class="username">
        {{comment.username}}
         <!-- <button class="delete" style="float: right; top: 0; right: 0; position: relative;"/> -->
      </p>
      <div class="says">
        <p>{{comment.context}}</p>
        <p style="color: grey; font-size: 14px;">{{ new Date(comment.timestamp) | format-date}}</p>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  components: {
  
  },
  props: ["comment"]
};
</script>

<style lang="scss" scoped>
.balloon {
  width: 100%;
  padding-top: 15px;
  margin-bottom: 20px;
  overflow: hidden;
}

.balloon .faceicon {
  float: left;
  margin-right: -50px;
  width: 50px;
}

.username {
  margin-left: 55px;
}

.balloon .faceicon img{
  width: 100%;
  height: auto;
  border-radius: 50%;
}
.balloon .chatting {
  width: 100%;
  text-align: left;
}

.says {
  display: inline-block;
  position: relative; 
  margin: 0 0 0 60px;
  padding: 10px;
  max-width: 100%;
  border-radius: 12px;
  background: #edf1ee;
}

.says:after {
  content: "";
  display: inline-block;
  position: absolute;
  top: 3px; 
  left: -19px;
  border: 8px solid transparent;
  border-right: 18px solid #edf1ee;
  -webkit-transform: rotate(35deg);
  transform: rotate(35deg);
}
.says p {
  margin: 0;
  padding: 0;
  word-break: break-all;
}

</style>