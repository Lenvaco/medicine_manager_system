import Vue from 'vue'
import App from './App.vue'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import permission from './components/permission'
import '@/icons' // 引入icon
import './router/index' // permission control
import router from './router/routers'
import '@/styles/index.scss' // global css

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(permission);

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
}).$mount('#app');
