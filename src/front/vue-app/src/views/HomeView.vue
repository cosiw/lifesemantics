<template>
  <div class="container mt-3">
  <h1>로그인</h1>
  <form @submit.prevent="login">
  <div class="mb-3 row">
    <label for="id"  class="col-sm-2 col-form-label">아이디 : </label>
    <div class="col-sm-10">
      <input type="text" v-model="userId" class="form-control" id="id">
    </div>
  </div>
  <div class="mb-3 row">
    <label for="pwd" class="col-sm-2 col-form-label">비밀번호 : </label>
    <div class="col-sm-10">
      <input type="password" v-model="userPwd" class="form-control" id="pwd">
    </div>
  </div>
  <div class="d-flex">
		  <div class="p-2 flex-fill d-grid">
				<button type="submit" class="btn btn-primary" @click="login()">로그인</button>
		  </div>
		  <div class="p-2 flex-fill d-grid">
				<button class="btn btn-primary" @click="join()">회원가입</button>
		  </div>
		</div>
</form>
</div>
</template>

<script>
import router from '@/router'
import axios from 'axios'
import store from '@/store'

export default {
  name: 'HomeView',
  data(){
    return {
      userId: '',
      userPwd: '',
      loggedIn : false
    }
  },
  methods: {
    login() {
      axios.post('http://localhost:8082/api/login', {
        userId: this.userId,
        userPwd: this.userPwd
      })
      .then((response) => {
        const token = response.data.accessToken;
        console.log("userIdx : " , response.data.userIdx);
        sessionStorage.setItem('token', token);
        this.loggedIn = true;
        
        if(token){
          axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          this.loggedIn = true;
        }
        store.commit('setUser', response.data);
        sessionStorage.setItem('user', JSON.stringify(response.data));
        this.$router.push({
          name: 'ListView',
          params: {login: response.data}
        });
        
      })
      .catch((error) =>{
        console.log(error);
        alert(error.response.data.message)
      });
    },
    join(){
    this.$router.push('user/join');
    }
  },

}
</script>

<style scoped>
a {
  color:black;
  text-decoration: none;
  font-weight: bold;
  font-size: 1.5rem;
}
</style>