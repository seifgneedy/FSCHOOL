<template>
  <v-app>
    <v-main>
      <div style="float: left">
        <v-navigation-drawer permanent>
          <v-list>
            <div align="left">
              <v-btn icon @click="goBack">
                <v-icon>mdi-keyboard-backspace</v-icon>
              </v-btn>
            </div>
            <v-list-item class="px-2"></v-list-item>
            <v-list-item-avatar>
              <v-img src="@/assets/add_course_icon.svg"></v-img>
            </v-list-item-avatar>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title id="mainItem" style="font-size: 20px">
                  <h2 v-text="coursecode"></h2>
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list nav dense>
            <v-list-item
              link
              @click="
                showAssignments = false;
                showPosts = true;
                showAnnouncement = false;
                showQuestions = false;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/Posts.png" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title id="mainItem"> Posts </v-list-item-title>
            </v-list-item>
            <v-list-item
              link
              @click="
                showAssignments = false;
                showPosts = false;
                showAnnouncement = true;
                showQuestions = false;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/Announcement.png" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title id="mainItem">
                Annoncements
              </v-list-item-title>
            </v-list-item>
            <v-list-item
              link
              @click="
                showAssignments = false;
                showPosts = false;
                showAnnouncement = false;
                showQuestions = true;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/Qs.png"></v-img>
                </v-list-item-avatar>
              </v-list-item-icon>

              <v-list-item-title id="mainItem"> Questions </v-list-item-title>
            </v-list-item>
            <v-list-item
              link
              @click="
                showAssignments = true;
                showPosts = false;
                showAnnouncement = false;
                showQuestions = false;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/assignments.png" />
                </v-list-item-avatar>
              </v-list-item-icon>

              <v-list-item-title id="mainItem"> Assignments </v-list-item-title>
            </v-list-item>
            <br /><br />
            <br /><br /><br />
            <v-btn block dark @click="signOut">Sign Out</v-btn>
          </v-list>
        </v-navigation-drawer>
      </div>
      <div align="center">
        <div v-if="showAssignments">
          <h2>Coming Soon: Assignments</h2>
        </div>
        <div v-if="showPosts">
          <user-posts :postType="'post'" :userRole="this.user.role" />
        </div>
        <div v-if="showAnnouncement">
          <user-posts :postType="'announcement'" :userRole="this.user.role" />
        </div>
        <div v-if="showQuestions">
          <user-posts :postType="'question'" :userRole="this.user.role" />
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import UserPosts from "../components/UserPosts.vue";
export default {
  name: "course",
  components: { UserPosts },
  data() {
    return {
      user: this.$store.getters.getUser,
      coursecode: this.$route.params.code,
      showPosts: true,
      showAnnouncement: false,
      showQuestions: false,
      showAssignments: false,
      // usersInCourse: [],
    };
  },
  methods: {
    signOut() {
      this.$store.commit("signOut");
      this.$router.push("/");
    },
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
