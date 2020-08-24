import Vue from 'vue'
import App from './app.vue'
import axios from 'axios'
import router from './router'
import filter from "./filter/filter";
Object.keys(filter).forEach(key => {
  Vue.filter(key, filter[key])
});
Vue.config.productionTip = false
Vue.prototype.$ajax = axios;
// 解决每次ajax请求，对应的sessionId不一致的问题
axios.defaults.withCredentials = true;


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
