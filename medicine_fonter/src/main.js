import Vue from 'vue'
import App from './App.vue'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import permission from './components/permission'
import './assets/icons' // icon
import './router/index' // permission control
import router from './router/routers'
import '@/styles/index.scss' // global css
import AFTableColumn from 'af-table-column'
import VCharts from 'v-charts'

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(AFTableColumn);
Vue.use(permission);
Vue.use(VCharts)

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
}).$mount('#app');
