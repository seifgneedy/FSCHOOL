<template>
  <v-app>
    <v-content>
      <div style="float: left">
        <v-navigation-drawer permanent>
          <v-list>
            <v-list-item class="px-2"></v-list-item>
              <v-list-item-avatar>
                <v-img src="@/assets/admin_avatar.svg"></v-img>
              </v-list-item-avatar>
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title class="text-h6"> Admin </v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list>

          <v-divider></v-divider>

          <v-list nav dense>
            <v-list-item link>
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/add_course_icon.svg" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title
                @click="
                  showAddEditCourse = true;
                  showAddTeacher = false;
                  showAddStudent = false;
                "
              >
                Add / Edit Course
              </v-list-item-title>
            </v-list-item>
            <v-list-item link>
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/teacher_avatar.svg" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title
                @click="
                  showAddEditCourse = false;
                  showAddTeacher = true;
                  showAddStudent = false;
                "
              >
                Add Teacher
              </v-list-item-title>
            </v-list-item>
            <v-list-item link>
              <v-list-item-icon>
                <v-list-item-avatar>
                  <v-img src="@/assets/student_avatar.svg" />
                </v-list-item-avatar>
              </v-list-item-icon>
              <v-list-item-title
                @click="
                  showAddEditCourse = false;
                  showAddTeacher = false;
                  showAddStudent = true;
                "
              >
                Add Student
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-navigation-drawer>
      </div>
      <div align="center">
        <div v-show="showAddEditCourse">
          <add-edit-course />
          <v-btn depressed color="primary" @click="addCourse">
            Add Course
          </v-btn>
        </div>
        <div v-show="showAddTeacher"></div>
        <div v-show="showAddStudent"></div>
      </div>
    </v-content>
  </v-app>
</template>

<script>
import AddEditCourse from "@/components/AddEditCourse.vue";
export default {
  name: "AdminView",
  components: {
    AddEditCourse,
  },
  data() {
    return {
      showAddEditCourse: true,
      showAddTeacher: false,
      showAddStudent: false,
      usersInCourse: [],
    };
  },
  methods: {
    addCourse() {
      console.log("add course");
    },
  },
};
</script>
