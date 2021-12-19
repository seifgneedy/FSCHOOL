import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userId:null
  },
  plugins: [createPersistedState()],
  mutations: {
    signIn(state,payload){
      state.userId=payload;
    },
    signOut(state){
      state.userId=null;
    }
  },
  getters:{
    getUser(state){
      return state.userId;
    }
  },
  actions: {},
  modules: {},
});
