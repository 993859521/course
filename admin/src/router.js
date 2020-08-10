import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Index from './views/index.vue'
import Welcome from './views/admin/welcome.vue'

Vue.use(Router)

export default new Router({
    mode: "history",
    base: process.env.BASE_URL,
    routes: [{
        path: "*",
        redirect: "/login",
    },{
        path: "/login",
        component: Login
    }, {
        path: "/index",
        component: Index,
        children: [{
            path: "welcome",
            name: "welcome",
            component: Welcome,
        }],

        },
    ]
})