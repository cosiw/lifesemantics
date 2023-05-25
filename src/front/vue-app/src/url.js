import axios from 'axios'
import { setInterceptors } from './common/interceptors'

function createInstance(){
    return axios.create({
        baseURL: http://localhost:8800/
    })
}

function createInstanceWithAuth() {
    const instance = axios.create({
        baseURL: http://localhost:8800/
    })
    return setInterceptors(instance)
}

const instance = createInstance()
const instanceAuth = createInstanceWithAuth()
export {instance, instanceAuth}