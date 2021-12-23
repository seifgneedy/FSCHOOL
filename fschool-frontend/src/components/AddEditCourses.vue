<template>
  <v-data-table
    :headers="headers"
    :items="courses"
    item-key="code"
    :loading="currentlyLoading"
    loading-text="Loading... Please wait"
    sort-by="code"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>Courses</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="networkError" max-width="340px">
          <v-card>
          <v-alert type="error">
            Network Error. Please try again later.
          </v-alert>
          <v-card-actions>
            <v-spacer></v-spacer>
          <v-btn color="blue darken-1" align="center" text @click="networkError = false"
            >OK</v-btn
          >
          </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog
          v-model="dialog"
          fullscreen
          hide-overlay
          transition="dialog-bottom-transition"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              New Course
            </v-btn>
          </template>
          <v-card>
            <v-toolbar dark color="primary">
              <v-toolbar-title>{{ formTitle }}</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-toolbar-items>
                <v-btn v-show="isAddingNewCourse" dark text @click="close">
                  Cancel
                </v-btn>
                <v-btn dark text @click="save"> Save </v-btn>
              </v-toolbar-items>
            </v-toolbar>
            <v-card-text>
              <v-container>
                <v-row v-if="isAddingNewCourse">
                  <v-alert v-show="invalidCourseCode" type="error">
                    Invalid course code
                  </v-alert>
                  <v-text-field
                    v-model="editedItem.code"
                    label="Course code"
                    required
                  ></v-text-field>
                </v-row>
                <v-row>
                  <v-text-field
                    v-model="editedItem.name"
                    label="Course name"
                    required
                  ></v-text-field>
                </v-row>
                <v-row v-if="!isAddingNewCourse">
                  <edit-course-members
                    :userRole="'teacher'"
                    :courseCode="editedItem.code"
                  ></edit-course-members>
                  <v-spacer></v-spacer>
                  <edit-course-members
                    :userRole="'student'"
                    :courseCode="editedItem.code"
                  ></edit-course-members>
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="530px">
          <v-card align="center">
            <v-card-title class="text-h5"
              >Are you sure you want to delete
              {{ editedItem.code }}?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="deleteItemConfirm"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon small class="mr-2" @click="editItem(item)"> mdi-pencil </v-icon>
      <v-icon small @click="deleteItem(item)"> mdi-delete </v-icon>
    </template>
  </v-data-table>
</template>
<script>
import EditCourseMembers from "./EditCourseMembers.vue";
import { AXIOS } from "../http-common.js";
export default {
  components: {
    EditCourseMembers,
  },
  data: () => ({
    dialog: false,
    dialogDelete: false,
    addMemberDialog: false,
    dialogDeleteMember: false,
    invalidCourseCode: false,
    currentlyLoading: true,
    networkError: false,
    courseOldName:'',
    headers: [
      {
        text: "Code",
        align: "start",
        value: "code",
      },
      {
        text: "Name",
        value: "name",
      },
      { text: "Actions", value: "actions", sortable: false },
    ],
    courses: [],
    editedIndex: -1,
    isAddingNewCourse: true,
    editedItem: {
      code: "",
      name: "",
    },
    defaultItem: {
      code: "",
      name: "",
    },
  }),

  computed: {
    formTitle() {
      return this.isAddingNewCourse
        ? "New Course"
        : `Edit Course: ${this.editedItem.code}`;
    },
  },

  watch: {
    dialog(val) {
      val || this.close();
    },
    dialogDelete(val) {
      val || this.closeDelete();
    },
  },

  created() {
    this.initialize();
  },

  methods: {
    async initialize() {
      // Getting current courses
      await AXIOS.get("admin/courses", {}).then((res) => {
        this.courses = res.data;
      }).catch(() => {this.networkError = true});
      this.currentlyLoading = false;
    },

    editItem(item) {
      this.editedIndex = this.courses.indexOf(item);
      this.editedItem.code = item.code;
      this.editedItem.name = item.name;
      this.isAddingNewCourse = false;
      this.dialog = true;
    },

    deleteItem(item) {
      this.editedIndex = this.courses.indexOf(item);
      this.editedItem = Object.assign({}, item);
      this.dialogDelete = true;
    },
    async deleteItemConfirm() {
      let response;
      await AXIOS.delete(`admin/course?code=${this.editedItem.code}`, {}).then(
        (res) => {
          response = res.data;
        }
      ).catch(() => {this.networkError = true});
      if (response) this.courses.splice(this.editedIndex, 1);
      this.closeDelete();
    },
    close() {
      this.dialog = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
        this.isAddingNewCourse = true;
        this.invalidCourseCode = false;
      });
    },
    closeDelete() {
      this.dialogDelete = false;
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      });
    },
    async save() {
      if (this.editedIndex > -1) {
        //Editing
        if(this.courses[this.editedIndex].name !== this.editedItem.name){
          await AXIOS.put(
            `admin/updateCourseName?courseCode=${this.editedItem.code}&courseName=${this.editedItem.name}`,
            {}
          ).catch(() => {this.networkError = true});
          Object.assign(this.courses[this.editedIndex], this.editedItem);
        }
      } else {
        // Adding new one
        let response;
        await AXIOS.post(
          `admin/course?code=${this.editedItem.code}&name=${this.editedItem.name}`,
          {}
        ).then((res) => {
          response = res.data;
        }).catch(() => {this.networkError = true});
        if (response) this.courses.push(this.editedItem);
        else {
          this.invalidCourseCode = true;
          return;
        }
      }
      this.close();
    },
  },
};
</script>
