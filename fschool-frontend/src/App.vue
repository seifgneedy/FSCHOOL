<template>
  <v-app>
    <v-app-bar height='50' app dark>
      <v-spacer>

      </v-spacer>

      <v-btn
        href="https://github.com/seifgneedy/FSCHOOL"
        target="_blank"
        text
      >
      <span>Code</span>
        <v-icon>mdi-open-in-new</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",

  data: () => ({
    //
  }),
};
</script>
