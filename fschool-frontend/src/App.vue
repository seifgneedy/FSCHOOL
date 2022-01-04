<template>
  <v-app>
    <v-app-bar height="50" dark color="#090a26">
      <span style="font-size: 25px; font-weight: bold">FSCHOOL</span>
      <v-spacer></v-spacer>
      <v-btn href="https://github.com/seifgneedy/FSCHOOL" target="_blank" text>
        <span style="font-size: 15px">Code </span>
        <v-icon style="margin-left: 10px">mdi-github</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <div align="center">
        <router-view />
      </div>
    </v-main>
  </v-app>
</template>
<script>
export default {
  name: "App",
  components: {},
  data: () => ({
    //
  }),
};
</script>
