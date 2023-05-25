<template>
  <div class="container mt-3">
  <h1>회원가입</h1>
  <form>
  <div class="mb-3 row">
    <label for="name" class="col-sm-2 col-form-label">이름 : </label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="staticEmail">
    </div>
  </div>
  <div class="mb-3 row">
    <label for="id" class="col-sm-2 col-form-label">아이디 : </label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="pwd">
    </div>
  </div>
  <div class="mb-3 row">
    <label for="pwd" class="col-sm-2 col-form-label">비밀번호 : </label>
    <div class="col-sm-10">
      <input type="password" class="form-control" id="pwd">
    </div>
  </div>
  <div>
        <button type="submit">회원가입</button>
  </div>
  </form>
</div>
</template>

<script>
import axios from 'axios'
export default {
  name: 'HomeView',
  methods: {
    getData() {
      axios.get('http://localhost:8082/api/hi')
      .then((response) => {
        console.log(response)
      })
      .catch((error) =>{
        console.log(error)
      })
    }
  }
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