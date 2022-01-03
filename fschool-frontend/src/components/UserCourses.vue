<template>
    <v-container fluid>
    <v-card-title class="text-h3 text--primary font-weight-bold"> Courses</v-card-title>
      <v-row dense>
        <v-col v-for="course in courses" :key="course.code">
          <v-card  color="teal accent-9" class="mx-auto" height="200" width="300"  elevation="5" shaped hover @click="goToCourse(course.code)" >
            <v-card-title class="text-h4 text--primary" v-text="course.code"></v-card-title>
            <v-card-text class="text-h5 text--primary" v-text="course.name"></v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
import { AXIOS } from "../http-common.js";
export default {
  props: ["userRole"],
  components: {},
  data: () => ({
    courses: [],

  }),


  created() {
    this.initialize();
  },

  methods: {

    async initialize() {
      //TODO get this user's courses instead
      // Getting current courses
      await AXIOS.get("courses", {})
        .then((res) => {
          this.courses = res.data;
        })
    },
    goToCourse(code){
      this.$router.push({path: `/${this.userRole}/${code}`})
    },
  },
};
</script>
