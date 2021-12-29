<template>
  <v-app>
    <v-main>
      <div style="float: left">
        <v-navigation-drawer permanent>
          <v-list>
            <v-list-item class="px-2"></v-list-item>
            <v-list-item-avatar>
              <v-img src="@/assets/admin_avatar.svg"></v-img>
            </v-list-item-avatar>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title 
                    id="mainItem"
                    style="font-size:20px"
                > Admin </v-list-item-title>
                
              </v-list-item-content>
            </v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list nav dense>
            <v-list-item
              link
              @click="
                showAddEditCourse = true;
                showAddTeacher = false;
                showAddStudent = false;
                showAddAdmin = false;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/add_course_icon.svg" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title
                id="mainItem"
              > Courses </v-list-item-title>
            </v-list-item>
            <v-list-item
              link
              @click="
                showAddEditCourse = false;
                showAddTeacher = true;
                showAddStudent = false;
                showAddAdmin=false;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/teacher_avatar.svg" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title
                id="mainItem"
              > Teachers </v-list-item-title>
            </v-list-item>
            <v-list-item
              link
              @click="
                showAddEditCourse = false;
                showAddTeacher = false;
                showAddStudent = true;
                showAddAdmin= false;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/student_avatar.svg" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title
                id="mainItem"
              > Students </v-list-item-title>
            </v-list-item>
            <v-list-item
              link
              @click="
                showAddEditCourse = false;
                showAddTeacher = false;
                showAddStudent = false;
                showAddAdmin = true;
              "
            >
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/admin_avatar.svg"></v-img>
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title 
                  id="mainItem"
              > Admins </v-list-item-title>
            </v-list-item> 
            <br><br> <br><br><br>
            <v-btn
              block
              dark
              @click="signOut">Sign Out</v-btn>
          </v-list>
        </v-navigation-drawer>
      </div>
      <div align="center">
        <div v-show="showAddEditCourse">
          <add-edit-course />
        </div>
        <div v-show="showAddTeacher">
          <user-table :userRole="'teacher'" />
        </div>
        <div v-show="showAddStudent">
          <user-table :userRole="'student'" />
        </div>
        <div v-show="showAddAdmin">
          <user-table :userRole="'admin'" />
        </div>
      </div>
    </v-main>
  </v-app>
</template>

<script>
import AddEditCourse from "@/components/AddEditCourses.vue";
import UserTable from "../components/UserTable.vue";
export default {
  name: "admin",
  components: {
    AddEditCourse,
    UserTable,
  },
  data() {
    return {
      user: this.$store.getters.getUser,
      showAddEditCourse: true,
      showAddTeacher: false,
      showAddStudent: false,
      showAddAdmin: false,
      usersInCourse: [],
    };
  },
  methods: {
    signOut() {
      this.$store.commit("signOut");
      this.$router.push("/");
    },
  },
  // created() {
  //   if (this.user == null) this.signOut();
  // },
};
</script>
<style scoped>
#mainItem{
  font-size: 16px;
  font-weight: bold;
}
</style>