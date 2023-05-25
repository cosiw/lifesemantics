<template>
    <div class="container mt-3">
    <h1>회원가입</h1>
    <form>
    <div class="mb-3 row">
      <label for="name" class="col-sm-2 col-form-label">이름 : </label>
      <div class="col-sm-10">
        <input type="text" class="form-control" :class="{active: active.name}" id="userName" v-model="result.userName">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="id" class="col-sm-2 col-form-label">아이디 : </label>
      <div class="col-sm-10">
        <input type="text" class="form-control" :class="{active: active.id}" id="userId" v-model="result.userId">
      </div>
    </div>
    <div class="mb-3 row">
      <label for="pwd" class="col-sm-2 col-form-label">비밀번호 : </label>
      <div class="col-sm-10">
        <input type="password" class="form-control" :class="{active: active.pwd}" id="userPwd" v-model="result.userPwd">
      </div>
    </div>
    </form>
    <div>
          <button class="btn btn-primary" @click="save">회원가입</button>
    </div>
  </div>
  </template>
  
  <script>
  import axios from 'axios'
  export default {
    name: 'JoinView',
    data(){
        return {
            result: {userName: '', userPwd: '', userId: ''},
            active: {userName: false, userPwd: false, userId: false}
        }
    },
    created() {},
    methods: {
        save() {
            axios.post('http://localhost:8082/api/join', this.result)
            .then((res) => {
                console.log(res)
                this.$router.push("/");
            })
            .catch((error) => {
                console.log(error);
                alert(error.response.data.message)
                
            })
        }
    }
  }
  </script>
  
  <style scoped>
.active{
    background-color: blanchedalmond;
}
  </style>