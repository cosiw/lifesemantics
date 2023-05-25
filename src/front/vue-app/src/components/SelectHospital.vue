<template>
  
</template>

<script>
import axios from 'axios'
export default {
    data() {
        return{
            hospitalResult: {hospitalName : '',hospitalAddr : '', hospitalPhone : '', hospitaldhsbjtCd : ''}
        }
    },
    methods: {
        getHospitalData(row){
           this.showDiv = !this.showDiv;
           this.hospitalResult.hospitalName = row.yadmNm;
           this.hospitalResult.hospitalAddr = row.addr;
           this.hospitalResult.hospitalPhone = row.telno;
        },
        
    }

}

</script>