<template>
  <v-app>
    <v-main>
      <div style="float: left">
        <v-btn icon @click="goBack">
          <v-icon>mdi-keyboard-backspace</v-icon>
        </v-btn>
      </div>
      <div align="center">
        <div>
          <h2 v-text="this.$route.params.code"></h2>
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "course",
  components: {},
  data() {
    return {
      user: this.$store.getters.getUser,
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
  },
};
</script>
<style scoped>
#mainItem {
  font-size: 16px;
  font-weight: bold;
}
</style>
