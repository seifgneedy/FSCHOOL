<template>
  <v-app>
    <v-main>
      <div style="float: left">
        <v-navigation-drawer permanent>
          <v-list>
            <v-list-item class="px-2"></v-list-item>
            <v-list-item-avatar>
              <v-img src="@/assets/teacher_avatar.svg"></v-img>
            </v-list-item-avatar>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title id="mainItem" style="font-size: 20px">
                  {{ this.user.email }}
                </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list nav dense>
            <v-list-item
              link
              @click="
                showCourses = true;
                showAssignments = false;
                showCalendar = false;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/add_course_icon.svg" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title id="mainItem"> Courses </v-list-item-title>
            </v-list-item>
            <v-list-item
              link
              @click="
                showCourses = false;
                showAssignments = true;
                showCalendar = false;
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
        <div v-if="showCourses">
          <user-courses :userRole="'teacher'" />
        </div>
        <div v-if="showAssignments">
          <course-assignments  :userRole="'teacher'" />
        </div>
        <div v-if="showCalendar">
          <h2>To Do Calendar</h2>
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import UserCourses from "../components/UserCourses.vue";
import CourseAssignments from "../components/CourseAssignments.vue";

export default {
  name: "teacher",
  components: {
    UserCourses,
    CourseAssignments,
  },
  data() {
    return {
      user: this.$store.getters.getUser,
      showCourses: true,
      showAssignments: false,
      showCalendar: false,
      courses: [],
    };
  },
  methods: {
    signOut() {
      this.$store.commit("signOut");
      this.$router.push("/");
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
