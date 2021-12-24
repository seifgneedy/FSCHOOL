import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    user:null
  },
  plugins: [createPersistedState()],
  mutations: {
    setUser(state, payload) {
      state.user= payload;
    },
    signOut(state) {
      state.user = null;
    },
  },
  getters: {
    getUser(state) {
      return state.user;
    },
  },
  actions: {},
  modules: {},
});
