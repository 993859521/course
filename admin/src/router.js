import Vue from 'vue'
import Router from 'vue-router'
import Login from './views/login.vue'
import Index from './views/index.vue'
import Welcome from './views/admin/welcome.vue'
import Chapter from './views/admin/chapter.vue'
import Course from './views/admin/course.vue'
import Section from './views/admin/section.vue'

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
        path: "/",
        component: Index,
        name:"index",
        children: [
            {
            path: "welcome",
            name: "welcome",
            component: Welcome,
        },
            {
                path: "business/chapter",
                name: "chapter",
                component: Chapter,
            },
            {
                path: "business/course",
                name: "course",
                component: Course,
            },
            {
                path: "business/section",
                name: "section",
                component: Section,
            }
        ],

        },
    ]
})