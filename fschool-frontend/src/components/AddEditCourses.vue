<template>
  <v-data-table
    :headers="headers"
    :items="courses"
    sort-by="code"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar
        flat
      >
        <v-toolbar-title>Courses</v-toolbar-title>
        <v-divider
          class="mx-4"
          inset
          vertical
        ></v-divider>
        <v-spacer></v-spacer>
        <v-dialog
          v-model="dialog"
          fullscreen
          hide-overlay
          transition="dialog-bottom-transition"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-btn
              color="primary"
              dark
              class="mb-2"
              v-bind="attrs"
              v-on="on"
            >
              New Course
            </v-btn>
          </template>
          <v-card>
            <v-toolbar
          dark
          color="primary"
        >
          <v-toolbar-title>{{ formTitle }}</v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-items>
            <v-btn
              v-show="isAddingNewCourse"
              dark
              text
              @click="close"
            >
              Cancel
            </v-btn>
            <v-btn
              dark
              text
              @click="save"
            >
              Save
            </v-btn>
          </v-toolbar-items>
        </v-toolbar>
            <v-card-text>
              <v-container>
                <v-row v-if="isAddingNewCourse">
                    <v-text-field 
                      v-model="editedItem.code"
                      label="Course code"
                    ></v-text-field>
                </v-row>
                <v-row>
                    <v-text-field
                      v-model="editedItem.name"
                      label="Course name"
                    ></v-text-field>
                </v-row>
                <v-row v-show="!isAddingNewCourse">
                    <!--////////////////////
                    ///////////
                    /////////////////
                    / Start of Members table inside edit course
                    //////////////////
                    /
                    /////-->
                    <edit-course-members :userRole="'teacher'" :courseCode="editedItem.code"></edit-course-members>
                    <v-spacer></v-spacer>
                    <edit-course-members :userRole="'student'" :courseCode="editedItem.code"></edit-course-members>
                <!--////////////////////
                    ///////////
                    /////////////////
                    / End of Members table inside edit course
                    //////////////////
                    /
                    /////-->
                </v-row>
              </v-container>
            </v-card-text>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="530px">
          <v-card align="center">
            <v-card-title class="text-h5">Are you sure you want to delete {{editedItem.code}}?</v-card-title>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
              <v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon
        small
        class="mr-2"
        @click="editItem(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        small
        @click="deleteItem(item)"
      >
        mdi-delete
      </v-icon>
    </template>
  </v-data-table>
</template>
<script>
import EditCourseMembers from "./EditCourseMembers.vue";
  export default {
    components: {
    EditCourseMembers
  },
    data: () => ({
      dialog: false,
      dialogDelete: false,
      addMemberDialog: false,
      dialogDeleteMember: false,
      headers:[
        {
          text: "Code",
          align: "start",
          value: "code",
        },
        {
          text: "Name",
          value: "name",
        },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      userHeaders:[
        {
          text: "ID",
          align: "start",
          value: "id",
        },
        {
          text: "Name",
          value: "name",
        },
        {
          text: "Role",
          value: "role",
        },
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      courses: [
        {
          code: "CS111",
          name: "Introduction to CS",
        },
        {
          code: "CS112",
          name: "Programming 1",
        },
        {
          code: "CS211",
          name: "Data Structures",
        },
        {
          code: "CS223",
          name: "Design Patterns",
        },
        {
          code: "CS311",
          name: "Algorithms",
        },
        {
          code: "CS322",
          name: "Networks",
        },
      ],
      editedIndex: -1,
      deletedMemberIndex: -1,
      isAddingNewCourse: true,
      addedUserId: '',
      editedItem: {
        code: '',
        name: '',
        members:[],
      },
      defaultItem: {
        code: '',
        name: '',
        members:[],
      },
    }),

    computed: {
      formTitle () {
        return this.isAddingNewCourse ? 'New Course' : `Edit Course: ${this.editedItem.code}`;
      },
    },

    watch: {
      dialog (val) {
        val || this.close()
      },
      dialogDelete (val) {
        val || this.closeDelete()
      },
    },

    created () {
      this.initialize()
    },

    methods: {
      initialize () {

      },

      editItem (item) {
        this.editedIndex = this.courses.indexOf(item)
        this.editedItem.code = item.code;
        this.editedItem.name = item.name;
        //Loading members here
        /////////
        this.editedItem.members = [];
        // this.editedItem.members.push()
        /////////
        //////
        this.isAddingNewCourse = false
        this.dialog = true
      },

      deleteItem (item) {
        this.editedIndex = this.courses.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialogDelete = true
      },
      deleteMember (item) {
        this.deletedMemberIndex = this.editedItem.members.indexOf(item)
        this.addedUserId = item.id;
        this.dialogDeleteMember = true
      },

      deleteItemConfirm () {
        this.courses.splice(this.editedIndex, 1)
        this.closeDelete()
      },
      deleteMemberConfirm(){
        this.editedItem.members.splice(this.deletedMemberIndex, 1);
        this.closeDeleteMember();
      },
      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
          this.isAddingNewCourse = true
        })
      },
      closeAddMemberDialog(){
        this.addMemberDialog = false;
        this.addedUserId = '';
      },

      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },
      closeDeleteMember(){
        this.dialogDeleteMember = false;
        this.addedUserId = '';
      },
      save () {
        if (this.editedIndex > -1) {//Editing
          Object.assign(this.courses[this.editedIndex], this.editedItem)
        } else {// Adding new one
          this.courses.push(this.editedItem)
        }
        this.close()
      },
      saveAddedMember(){
        // Send id
        // this.addedUserId
        // get user data 
        // if success add it to the table
        this.editedItem.members.push({id:'1512',name:'111', role:'Student'});
        this.closeAddMemberDialog();
      },
    },
  }
</script>