<template>
  <v-container fluid>
    <v-row justify="end">
      <v-col>
        <v-card-title
          class="text-h3 text--primary font-weight-bold"
          @click="initialize()"
        >
          {{ postType[0].toUpperCase() + postType.substring(1) + "s" }}
        </v-card-title>
      </v-col>
      <v-col>
        <v-btn icon @click="initialize()" color="primary" dark>
          <v-icon>mdi-refresh</v-icon>
        </v-btn>
        <v-dialog
          v-model="postdialog"
          persistent
          :retain-focus="false"
          max-width="600px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              icon
              v-show="canAdd"
              color="primary"
              dark
              v-bind="attrs"
              v-on="on"
              @click="addpostError = false"
            >
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">NEW {{ postType.toUpperCase() }}</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4"> </v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      name="Title"
                      label="Title"
                      :error-messages="titleError"
                      v-model="newpost.title"
                      @input="$v.newpost.title.$touch()"
                      @blur="$v.newpost.title.$touch()"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-textarea
                      name="Body"
                      label="Body"
                      :error-messages="postBodyError"
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
            </v-card-text>
            <v-card-actions>
              <v-alert
                v-show="addpostError"
                type="error"
                style="font-size: 19px; font-weight: bold"
                >Error adding post:{{ postError }}</v-alert
              >
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closePostDialog()">
                Cancel
              </v-btn>
              <v-btn color="blue darken-1" text @click="addpost()"> Add </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
    <v-col v-for="post in posts" :key="post.id">
      <v-card color="#0F0639" dark max-width="600">
        <v-card-title>
          <v-icon large left> </v-icon>

          <span class="text-h6 font-weight-light" v-text="post.title"></span>
        </v-card-title>

        <v-card-text class="text-h5 font-weight-bold" v-text="post.body">
        </v-card-text>

        <v-card-actions>
          <v-list-item class="grow">
            <v-list-item-avatar color="grey darken-3">
              <v-img class="elevation-6" src="@/assets/publisher.svg"></v-img>
            </v-list-item-avatar>

            <v-card-action>
              <v-text color="deep-purple lighten-2">
                {{ post.publisher.firstName + " " + post.publisher.lastName }}
              </v-text>
            </v-card-action>
            <v-row align="center" justify="end">
              <span class="subheading mr-2"
                >{{ new Date(post.date).toLocaleString("en-GB") }}
              </span>
              <span class="mr-1">||</span>
              <v-icon class="mr-1" @click="openPost(post.id)">
                mdi-comment-multiple-outline
              </v-icon>
            </v-row>
            <v-dialog
              v-model="showComments"
              max-width="800px"
              :retain-focus="false"
            >
              <v-card align="center">
                <v-col v-for="comment in comments" :key="comment.id">
                  <v-card class="mx-auto" color="#0F0639" dark max-width="600">
                    <v-card-text
                      class="text-h5 font-weight-bold"
                      v-text="comment.body"
                    >
                    </v-card-text>
                    <v-card-actions>
                      <v-list-item class="grow">
                        <v-list-item-avatar color="grey darken-3">
                          <v-img
                            class="elevation-6"
                            src="@/assets/publisher.svg"
                          ></v-img>
                        </v-list-item-avatar>
                        <v-card-action>
                          <v-text color="deep-purple lighten-2">
                            {{
                              comment.publisher.firstName +
                              " " +
                              comment.publisher.lastName
                            }}
                          </v-text>
                        </v-card-action>
                        <v-row align="center" justify="end">
                          <span class="subheading mr-2"
                            >{{
                              new Date(comment.date).toLocaleString("en-GB")
                            }}
                          </span>
                        </v-row>
                      </v-list-item>
                    </v-card-actions>
                  </v-card>
                </v-col>
                <v-card-actions>
                  <v-spacer />
                  <v-btn color="blue darken-1" dark @click="closePost()"
                    >Close</v-btn
                  >
                  <v-spacer />
                  <v-row justify="end">
                    <v-dialog
                      v-model="commentdialog"
                      :retain-focus="false"
                      max-width="600px"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          color="primary"
                          dark
                          v-bind="attrs"
                          @click="addCommentError = false"
                          v-on="on"
                        >
                          Add comment
                        </v-btn>
                      </template>
                      <v-card>
                        <v-card-title>
                          <span class="text-h5">NEW COMMENT</span>
                        </v-card-title>
                        <v-card-text>
                          <v-container>
                            <v-row>
                              <v-col cols="12">
                                <v-textarea
                                  name="Comment"
                                  label="Comment"
                                  :error-messages="commentBodyError"
                                  v-model="newcomment.body"
                                  outlined
                                  type="text"
                                  @input="$v.newcomment.body.$touch()"
                                  @blur="$v.newcomment.body.$touch()"
                                  required
                                ></v-textarea>
                              </v-col>
                            </v-row>
                          </v-container>
                        </v-card-text>
                        <v-card-actions>
                          <v-alert
                            v-show="addCommentError"
                            type="error"
                            style="font-size: 19px; font-weight: bold"
                            >Error adding post:{{ CommentError }}</v-alert
                          >
                          <v-spacer></v-spacer>
                          <v-btn
                            color="blue darken-1"
                            text
                            @click="closeCommentDialog()"
                          >
                            Cancle
                          </v-btn>
                          <v-btn
                            color="blue darken-1"
                            text
                            @click="addComment()"
                          >
                            Add
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                  </v-row>
                  <v-spacer />
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-list-item>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-container>
</template>
<script>
import { AXIOS } from "../http-common.js";
import { required } from "vuelidate/lib/validators";
export default {
  props: ["postType", "userRole"],
  components: {},
  watch: {
    postType: {
      immediate: true,
      handler() {
        if (this.userRole == "teacher" && this.postType == "question")
          this.canAdd = false;
        else if (this.userRole == "student" && this.postType == "announcement")
          this.canAdd = false;
        else this.canAdd = true;
        this.initialize();
      },
    },
  },
  data: () => ({
    canAdd: true,
    showComments: false,
    posts: [],
    comments: [],
    postdialog: false,
    commentdialog: false,
    postError: "",
    CommentError: "",
    addpostError: false,
    addCommentError: false,
    currentPost: "",
    currentCourse: "",
    newpost: {
      type: "",
      title: "",
      body: "",
    },
    newcomment: {
      body: "",
    },
    defultnewcomment: {
      body: "",
    },
    defultnewpost: {
      type: "",
      title: "",
      body: "",
    },
  }),
  validations: {
    newpost: {
      title: {
        required,
      },
      body: {
        required,
      },
    },
    newcomment: {
      body: {
        required,
      },
    },
  },

  methods: {
    async initialize() {
      this.posts = [];
      this.currentCourse = this.$route.params.code;
      await AXIOS.get(
        `courses/${this.currentCourse}/?postType=${this.postType}`,
        {}
      ).then((res) => {
        this.posts = res.data;
      });
    },

    async openPost(postId) {
      //get post comments
      await AXIOS.get(`comments/${postId}`, {}).then((res) => {
        this.comments = res.data;
      });
      this.showComments = true;
      this.currentPost = postId;
    },
    //sending posts
    async addpost() {
      this.newpost.type = this.postType;
      this.$v.$touch();
      if (
        this.$v.newpost.title.$invalid ||
        this.$v.newpost.body.$invalid
      )
        return;
      let response,
        networkError = false;
      await AXIOS.post(`post/?courseCode=${this.currentCourse}`, this.newpost)
        .then((res) => {
          response = res.data;
        })
        .catch(() => {
          networkError = true;
          response = 0;
        });
      if (response != 0 && response != null) {
        this.closePostDialog();
        this.posts.unshift(response);
      } else if (networkError) {
        this.addpostError = true;
        this.postError = "Network Error,Try Again";
      }
    },
    //sending comments
    async addComment() {
      this.$v.$touch();
      if (this.$v.newcomment.body.$invalid) return;
      let response,
        networkError = false;
      await AXIOS.post(`comment/?postId=${this.currentPost}`, this.newcomment)
        .then((res) => {
          response = res.data;
        })
        .catch(() => {
          networkError = true;
          response = 0;
        });
      if (response != 0 && response != null) {
        this.comments.push(response);
        this.closeCommentDialog();
      } else if (networkError) {
        this.CommentError = true;
        this.CommentError = "Network Error,Try Again";
      }
    },
    closePost() {
      this.showComments = false;
    },
    closeCommentDialog() {
      this.$v.$reset();
      this.commentdialog = false;
      this.$nextTick(() => {
        this.newcomment = Object.assign({}, this.defultnewcomment);
      });
    },
    closePostDialog() {
      this.$v.$reset();
      this.postdialog = false;
      this.$nextTick(() => {
        this.newpost = Object.assign({}, this.defultnewpost);
      });
    },
  },
  computed: {
    titleError() {
      const errors = [];
      if (!this.$v.newpost.title.$dirty) return errors;
      !this.$v.newpost.title.required && errors.push("title required");
      return errors;
    },
    postBodyError() {
      const errors = [];
      if (!this.$v.newpost.body.$dirty) return errors;
      !this.$v.newpost.body.required && errors.push("body required");
      return errors;
    },
    commentBodyError() {
      const errors = [];
      if (!this.$v.newcomment.body.$dirty) return errors;
      !this.$v.newcomment.body.required && errors.push("body required");
      return errors;
    },
  },
};
</script>
