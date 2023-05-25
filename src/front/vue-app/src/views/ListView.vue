<template>
    <div class="three-columns-layout">
      <div class="column">
        <h2>병원리스트</h2>
        <div class="container mt-3">
	  <div class="btn-group">
        <input type="text" v-model="searchKeyword">
	    <button @click="getHospitalListData()">검색</button>
	  </div>
	  <table class="table table-hover mt-3">
	    <thead class="table-dark">
	      <tr>
	      	<th>병원</th>
	      </tr>
	    </thead>
	    <tbody>
	      <tr class="cursor-pointer" v-for="row in hospitalListResult" v-bind:key="row.ykiho" 
		      v-on:click="$event => getHospitalData(row)">
	        <td>{{row.yadmNm}}</td>
	      </tr>
	    </tbody>
	  </table>
    <infinite-loading @infinite="loadMore"></infinite-loading>
	</div>
      </div>
      <div class="column" >
        <h2>예약리스트</h2>
        <table class="table table-hover mt-3">
        <thead class="table-dark">
	      <tr>
	      	<th>예약</th>
	      </tr>
	    </thead>
        <tbody>
	      <tr class="cursor-pointer" v-for="row in reservationListResult" v-bind:key="row.reservationIdx" 
		  v-on:click="$event => getReservationData(row)">
	        <td>{{row.hospitalRes.hospitalName}}</td>
	      </tr>
	    </tbody>
    </table>
    <infinite-loading @infinite="reservationMore" :identifier="infiniteId"></infinite-loading>
      </div>
      <div class="column">
        <h2>상세정보</h2>
        <div v-if="showHospital || showReservation">
            <div class="hospital-card">
                <h4>병원 정보</h4>
                <div class="form-group">
                    <label for="hospital" class="col-sm-2 col-form-label">병원명 :</label>
                    <p class="abc" v-if="showHospital" >{{ hospitalResult.hospitalName }}</p>
                    <p class="abc" v-if="showReservation" >{{ reservationResult.hospitalRes.hospitalName}}</p>
                </div>
                <div class="form-group">
                    <label for="hospital" class="col-sm-2 col-form-label">주소 :</label>
                    <p class="abc" v-if="showHospital">{{ hospitalResult.hospitalAddr }}</p>
                    <p class="abc" v-if="showReservation" >{{ reservationResult.hospitalRes.hospitalAddr}}</p>
                </div>
                <div class="form-group">
                    <label for="hospital" class="col-sm-2 col-form-label">전화번호 :</label>
                    <p class="abc" v-if="showHospital">{{ hospitalResult.hospitalTel }}</p>
                    <p class="abc" v-if="showReservation" >{{ reservationResult.hospitalRes.hospitalTel}}</p>
                </div>
            </div>
            <div class="hospital-card">
                <h4>진료 신청</h4>
                <div class="form-group">
                    <label for="hospital" class="col-sm-2 col-form-label">이름 :</label>
                    <input type="text" v-if="showHospital" class="form-control" id="userName" v-model = "user.userName" readonly />
                    <input type="text" v-if="showReservation" class="form-control" id="userId" v-model=reservationResult.userRes.userName readonly />
                </div>
                <div class="form-group">
                    <label for="hospital" class="col-sm-2 col-form-label">전화번호 :</label>
                    <input type="text" v-if="showHospital" class="form-control" id="cellphone" v-model=reservation.cellphone>
                    <input type="text" v-if="showReservation" class="form-control" id="cellphone" v-model=reservationResult.cellphone>
                </div>
                <div class="form-group">
                    <label for="hospital" class="col-sm-2 col-form-label">증상 :</label>
                    <input type="text" v-if="showHospital" class="form-control" id="symptom" v-model=reservation.symptom>
                    <input type="text" v-if="showReservation" class="form-control" id="symptom" v-model=reservationResult.symptom>
                </div>
                <div class="form-group">
                    <label for="hospital" class="col-sm-2 col-form-label">예약일시 :</label>
                    <Datepicker v-model="selectedDate" :format="dateFormat"></Datepicker>
                    
                    <!-- <input type="text" v-if="showHospital" class="form-control" id="reservationDate" v-model=reservation.reservationDate>
                    <input type="text" v-if="showReservation" class="form-control" id="reservationDate" v-model=reservationResult.reservationDate> -->
                </div>
                <img class="preview-image" v-if="previewImage" :src="previewImage" alt="미리보기 이미지">
                <img :src= "symptomImage" v-if="!previewImage"/>
                <form @submit.prevent="uploadImage">
                    <input type="file" id="imageUpload" accept="image/*" @change="handleFileSelect">
                </form>
              </div>
		          <div class="p-2 flex-fill d-grid">
				        <button class="btn btn-primary" v-if="showHospital"  @click="saveReservation()">예약하기</button>
              </div>
              <div class="p-2 flex-fill bd-highlight">
                <button class="btn btn-primary" v-if="showReservation"  @click="updateReservation()">수정하기</button>
                <button class="btn btn-primary" v-if="showReservation"  @click="deleteReservation()">삭제하기</button>
		          </div>
        </div>
    </div>
    </div>
  </template>
  
<script>
import axios from 'axios'
import router from '@/router'
import store from '@/store'
import Datepicker from 'vue3-datepicker'
import {ref, reactive, defineComponent} from 'vue';
import {ko} from 'date-fns/locale';

export default {
  components: {
    Datepicker,
  },
    name: 'ListView',
    mounted(){
      console.log("moutnted")
      console.log(this.$route);
    },

    data(){
        return{
            hospitalListResult: [],
            reservationListResult: [],
            hospitalResult: {hospitalIdx : '', hospitalName : '',hospitalAddr : '', hospitalTel : '', hospitalYkiho : ''},
            reservationResult: {hospitalRes : {hospitalIdx : '', hospitalName : '',hospitalAddr : '', hospitalTel : ''},  
            userRes : {userIdx : '', userId: '', userName: ''}, reservationTel : '', symptom : '', reservationDate : '', image: ''},
            showHospital: false,
            showReservation:false,
            selectedImage: null,
            previewImage: null,
            searchKeyword: '',
            currentPage: 1,
            pageSize: 10,
            infiniteId: Date.now(),
            user: {},
            reservation: {symptom : '', reservationDate : '', cellphone :'',
             hospitalReq:{hospitalIdx : '', hospitalName : '',hospitalAddr : '', hospitalTel : '', hospitalYkiho : ''}},
            symptomImage : null,
            selectedDate : null,
            dateFormat : 'yyyy-MM-dd'
        };
    },
    created() {
     
       const token = sessionStorage.getItem('token');
       if(token){
          axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;
          store.commit('setUser', JSON.parse(sessionStorage.getItem('user')));
          this.loggedIn = true;
       }
        this.user = this.$store.state.user;
        
        this.getHospitalListData()
        this.getReservationListData()
    },
    methods: {
      loadMore($state){
        axios
        .get('http://localhost:8082/login/api/hospital', {
          params: {
            page: this.currentPage,
            size: this.pageSize,
          },
        })
        .then((response) => {
          const data = response.data.response.body.items.item;
          if(data.length > 0){
            this.items = this.items.concat(data);
            this.currentPage++;
            $state.loaded();
          } else {
            $state.complete();
          }
          
        })
        .catch((error) => {
          console.error(error);
        })
      },
        getHospitalListData($state) {
            axios
            .get('http://localhost:8082/login/api/hospital', {
                params: {
                    page: this.currentPage,
                    size: this.pageSize,
                    hospitalName: this.searchKeyword || undefined
                }
            })
            .then((response) => {
                const data = response.data.response.body.items.item;
                if(data.length > 0){
                  this.hospitalListResult = this.hospitalListResult.concat(data);
                  this.currentPage++;
                  $state.loaded();
                }else {
                  $state.complete();
                }
    
            })
            .catch((error) => {
                console.log(error);
            
            });
        },
        getHospitalData(row){

          if(this.showHospital == false)
           this.showHospital = !this.showHospital;
           if(this.showReservation == true)
           this.showReservation = false;
           this.hospitalResult.hospitalIdx = row.hospitalIdx;
           this.hospitalResult.hospitalName = row.yadmNm;
           this.hospitalResult.hospitalAddr = row.addr;
           this.hospitalResult.hospitalTel = row.telno;
           this.hospitalResult.hospitalYkiho = row.ykiho;
           this.symptomImage = '';
           this.previewImage = '';
           this.selectedDate = '';
        },
        getReservationData(row){
            if(this.showReservation == false)
            this.showReservation = !this.showReservation;
            if(this.showHospital == true)
            this.showHospital = false;
            this.previewImage = '';
            axios
            .get('http://localhost:8082/login/api/reservation/' + row.reservationIdx)
            .then(response => {
              this.reservationResult = response.data;
              this.selectedDate = new Date(response.data.reservationDate);
              this.symptomImage = 'http://localhost:8082/api/reservation' + this.reservationResult.image;
            })
            .catch((error) => {
              console.log(error);
            })
        },
        getReservationListData(){
            axios
            .get('http://localhost:8082/login/api/reservation')
            .then((response) => {
                this.reservationListResult = response.data;
                console.log(response.data);
            })
            .catch((error) => {
                console.log(error);
            })
        },
        find(row){
            console.log("row : " , row);
            this.showDiv = !this.showDiv;
            this.previewImage = null;
        },
        handleFileSelect(event) {
            const file = event.target.files[0];
            this.selectedImage = file;
            this.previewImage = URL.createObjectURL(file);
        },
        uploadImage(){
         if (this.selectedImage) {
            const reader = new FileReader();
            reader.onload = () => {
                this.previewImage = reader.result;
            };
            reader.readAsDataURL(this.selectedImage);
          }  
        },
        saveReservation(){
          console.log("select", this.selectedDate);
          const formData = new FormData();
          this.reservation.hospitalReq = this.hospitalResult;
          this.reservation.reservationDate = this.selectedDate;
          formData.append('image', this.selectedImage);
          formData.append('ReservationReq', new Blob([JSON.stringify(this.reservation)], {type: "application/json"}))
          axios
          .post('http://localhost:8082/login/api/reservation', formData,{
            headers:{
              'Content-Type' : 'multipart/form-data'
            }
          })
          .then(response => {
            this.previewImage = false;
            this.getReservationListData();
            console.log(response);
          })
          .catch(error => {
            console.log(error);
          })
        },
        updateReservation(){
          const reservationIdx = this.reservationResult.reservationIdx;
          console.log(this.reservationResult.reservationDate);
          const formData = new FormData();
          const UpdateReservationRequestDTO = {symptom : this.reservationResult.symptom, 
            cellphone : this.reservationResult.cellphone, reservationDate : this.reservationResult.reservationDate};

          formData.append('image', this.selectedImage);
          formData.append('req', new Blob([JSON.stringify(UpdateReservationRequestDTO)], {type: "application/json"}));
          axios
          .put('http://localhost:8082/login/api/reservation/' + reservationIdx, formData, {
            headers: {
              'Content-Type' : 'multipart/form-data'
            }
          })
          .then(response => {
            
            this.getReservationListData();
            
          })
          .catch(error => {
            console.log(error);
          })
        },
        deleteReservation(){
          const reservationIdx = this.reservationResult.reservationIdx;
          axios
          .delete('http://localhost:8082/login/api/reservation/' + reservationIdx)
          .then(response => {
            this.getReservationListData();
            console.log(response);
          })
          .catch(error => {
            console.log(error);
          })
        }
    }
}
</script>
  <style>
  .three-columns-layout {
    display: flex;
  }
  
  .column {
    flex: 1;
    padding: 20px;
  }
  .hospital-card{
    text-align: left;
    padding-top : 20px;
  }
  .form-group {
      display: flex;
      align-items: center;
 
  }
.preview-image {
  max-width: 300px;
  max-height: 300px;
  margin-top: 10px;
}
img {
    border: 1px solid #d8d8d8;
    width: 70%;
    margin: 40px;
    box-shadow: 0px .5px 1px #d8d8d8;
  }
  </style>