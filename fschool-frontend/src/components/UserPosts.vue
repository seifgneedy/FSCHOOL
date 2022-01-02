<template>
  <v-container fluid>
    <v-card-title class="text-h3 text--primary font-weight-bold">
      {{ postType }} </v-card-title
    >
    

 <v-row justify="end">
    <v-dialog
      v-model="dialog"
      persistent
      max-width="600px"
    >
      <template v-slot:activator="{ on, attrs }">
        <v-btn
          color="primary"
          dark
          v-bind="attrs"
          v-on="on"
          @click="addpostError=false"
        >
            New {{ postType }}
        </v-btn>
      </template>
      <v-card>
        <v-card-title>
          <span class="text-h5">NEW POST</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                  <div v-if="userRole =='teacher'">
                <v-select
                  label="post type*"
                  :items="['announcement', 'post' ,'question']"
                   required
                    @change="$v.newpost.type.$touch()"
                        @blur="$v.newpost.type.$touch()"
                ></v-select> 
                </div>
                 <div v-if="userRole =='student'">
                <v-select
                  label="post type*"
                  :items="['post' ,'question']"
                   required
                    @change="$v.newpost.type.$touch()"
                        @blur="$v.newpost.type.$touch()"
                ></v-select> 
                </div>
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
               
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="4"
              >
                <v-text-field
                  name="Title/summary*"
                  label="Title/summary*"
                  v-model="newpost.title"
                  @input="$v.newpost.title.$touch()"
                  @blur="$v.newpost.title.$touch()"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-textarea
                   name="post body"
                  label="post body"
                  v-model="newpost.body"
                  outlined
                  type="text"
                   @input="$v.newpost.body.$touch()"
                    @blur="$v.newpost.body.$touch()"
                  required
                ></v-textarea>
              </v-col>
              
            </v-row>
          </v-container>
          <small>*indicates required field</small>
        </v-card-text>
        <v-card-actions>
         <v-alert
                  v-show="addpostError"
                  type="error"
                  style="font-size: 19px; font-weight: bold"
                  >Error adding post:{{ postError }}</v-alert
                >
          <v-spacer></v-spacer>
          <v-btn
            color="blue darken-1"
            text
            @click="close()"
          >
            Close
          </v-btn>
          <v-btn
            color="blue darken-1"
            text
            @click="addpost()"
          >
            Add
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-row>






    <v-col v-for="post in posts" :key="post.code">
      <v-card class="mx-auto" color="#0F0639" dark max-width="600">
        <v-card-title>
          <v-icon large left> </v-icon>

          <span class="text-h6 font-weight-light">"Post Title"</span>
        </v-card-title>

        <v-card-text class="text-h5 font-weight-bold">
          "Post body"
        </v-card-text>

        <v-card-actions>
          <v-list-item class="grow">
            <v-list-item-avatar color="grey darken-3">
              <v-img
                class="elevation-6"
                alt=""
                src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
              ></v-img>
            </v-list-item-avatar>

            <v-card-actions>
              <v-text color="deep-purple lighten-2" text>
                {{useremail}}
              </v-text>
            </v-card-actions>

            <v-row align="center" justify="end">
              <span class="subheading mr-2">"time"</span>
              <span class="mr-1">||</span>
              <v-icon class="mr-1" @click="MakeComment()">
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
import {
  required,
  email,
} from "vuelidate/lib/validators";
export default {
  props: ["postType","userRole","courseCode","userEmail"],
  components: {},
  data: () => ({
    posts: [],
    dialog:false,
    postError:"",
    addpostError:false,
    newpost: {
      type: "",
      title: "",
      body: "",
      email: "",    
      Coursecode: "",
    },
    defultnewpost: {
      type: "",
      title: "",
      body: "",
      email: "",    
      Coursecode: "",
    },
  }),
  validations: {
    newpost: {
      type: {
        required,
      },
      title: {
        required,
      },
      body: {
        required,
      },
      email: {
          required,
        email,
      },
      Coursecode: {
        required,
      },
    },
  },

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
      async addpost() {
      if (
        this.$v.newpost.type.$invalid ||
        this.$v.newpost.title.$invalid ||
        this.$v.newpost.body.$invalid
      )
        return;
         this.newpost.email = this.userEmail;
         this.newpost.Coursecode = this.CourseCode;
         let response,networkError=false;
         await AXIOS.post("admin/user", this.newpost)
        .then((res) => {
          response = res.data;
        })
        .catch(() => {
          networkError = true;
          response = 0;
        });
         if (response != 0) {
           this.close();
         }
         else if (networkError){
            this.addpostError=true;
          this.postError = "Network Error,Try Again";
        }
      },
      MakeComment() {
      },
     close() {
      this.dialog = false;
      this.$nextTick(() => {
      this.newUser = Object.assign({}, this.defultnewpost);
      });
    },
  },
};
</script>
