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
          max-width="500px"
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
            <v-card-title>
              <span class="text-h5">{{ formTitle }}</span>
            </v-card-title>

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
                <v-row>
                    <v-textarea
                        v-model="editedItem.newMembers"
                        label="New Members"
                        hint="Please enter comma separated IDs"
                        ></v-textarea>
                </v-row>
              </v-container>
            </v-card-text>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                color="blue darken-1"
                text
                @click="close"
              >
                Cancel
              </v-btn>
              <v-btn
                color="blue darken-1"
                text
                @click="save"
              >
                Save
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDelete" max-width="530px">
          <v-card align="center">
            <v-card-title class="text-h5">Are you sure you want to delete this course?</v-card-title>
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
    <template v-slot:no-data>
      <v-btn
        color="primary"
        @click="initialize"
      >
        Reset
      </v-btn>
    </template>
  </v-data-table>
</template>
<script>
  export default {
    data: () => ({
      dialog: false,
      dialogDelete: false,
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
      isAddingNewCourse: true,
      editedItem: {
        code: '',
        name: '',
        newMembers:'',
      },
      defaultItem: {
        code: '',
        name: '',
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
        this.editedItem = Object.assign({}, item)
        this.isAddingNewCourse = false
        this.dialog = true
      },

      deleteItem (item) {
        this.editedIndex = this.courses.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialogDelete = true
      },

      deleteItemConfirm () {
        this.courses.splice(this.editedIndex, 1)
        this.closeDelete()
      },

      close () {
        this.dialog = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
          this.isAddingNewCourse = true
        })
      },

      closeDelete () {
        this.dialogDelete = false
        this.$nextTick(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        })
      },

      save () {
        if (this.editedIndex > -1) {
          Object.assign(this.courses[this.editedIndex], this.editedItem)
        } else {
          this.courses.push(this.editedItem)
        }
        this.close()
      },
    },
  }
</script>