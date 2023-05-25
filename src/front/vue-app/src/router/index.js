import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/list',
    name: 'ListView',
    component: () => import(/* webpackChunkName: "user" */ '../views/ListView.vue')
  },
  {
    path: '/list',
    name: 'ListView/:hospitalName/:ykiho',
    component: () => import(/* webpackChunkName: "user" */ '../views/ListView.vue')
  },
  {
    path: '/list',
    name: 'ListView/:reservationIdx',
    component: () => import(/* webpackChunkName: "user" */ '../views/ListView.vue')
  },
  {
    path: '/user/findById',
    name: 'SelectView',
    component: () => import(/* webpackChunkName: "user" */ '../views/SelectView.vue')
  },
  {
    path: '/user/editById',
    name: 'UpdateView',
    component: () => import(/* webpackChunkName: "user" */ '../views/UpdateView.vue')
  },
  {
    path: '/user/save',
    name: 'CreateView',
    component: () => import(/* webpackChunkName: "user" */ '../views/CreateView.vue')
  },
  {
    path: '/user/join',
    name: 'JoinView',
    component: () => import(/* webpackChunkName: "user" */ '../views/JoinView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
