
<template>
<v-container fluid>
   <v-card-title class="text-h3 text--primary font-weight-bold"> {{PostType}}</v-card-title>
  <v-col v-for="post in posts" :key="post.code">
  <v-card
    class="mx-auto"
    color="#0F0639"
    dark
    max-width="600"
  >
    <v-card-title>
      <v-icon
        large
        left
      >
      </v-icon>
      
      <span class="text-h6 font-weight-light">"Post Title"</span>
    </v-card-title>

    <v-card-text class="text-h5 font-weight-bold">
      "Post body"
    </v-card-text>

    <v-card-actions>
      <v-list-item class="grow" >
        <v-list-item-avatar color="grey darken-3" >
          <v-img
            class="elevation-6"
            alt=""
            src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
          ></v-img>
        </v-list-item-avatar>


        <v-card-actions>
      <v-text
        color="deep-purple lighten-2"
        text
      >
        user@fschool.com
      </v-text>
    </v-card-actions>
        

        <v-row
          align="center"
          justify="end"
        >
        <span class="subheading mr-2">"time"</span>
          <span class="mr-1">||</span>
          <v-icon class="mr-1"  @click="MakeComment()
          ">
            mdi-comment-multiple-outline
          </v-icon>
         
        </v-row>
      </v-list-item>
    </v-card-actions>
   
  </v-card>
  </v-col>
 </v-container>
</template>
<script>
import { AXIOS } from "../http-common.js";
export default {
  props: ["PostType"],
  components: {},
  data: () => ({
    posts: [],

  }),


  created() {
    this.initialize();
  },

  methods: {

    async initialize() {
      //TODO get this course's posts instead
      // Getting  courses
      await AXIOS.get("admin/courses", {})
        .then((res) => {
          this.posts = res.data;
        })
        .catch(() => {
          this.networkError = true;
        });
      this.currentlyLoading = false;
    },
    goToCourse(code){
      this.$router.push({path: `/${this.userRole}/${code}`})
    },
    MakeComment(){
      console.log("sssssssssssss");
    },
  },
};
</script>
