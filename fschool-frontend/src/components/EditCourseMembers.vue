<template>
  <v-data-table
    :headers="userHeaders"
    :items="members"
    loading
    loading-text="Loading... Please wait"
    sort-by="ID"
    class="elevation-1"
  >
    <template v-slot:top>
      <v-toolbar flat>
        <v-toolbar-title>{{ userRole.toUpperCase() }}</v-toolbar-title>
        <v-divider class="mx-4" inset vertical></v-divider>
        <v-spacer></v-spacer>
        <v-dialog v-model="addMemberDialog" max-width="500px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="primary" dark class="mb-2" v-bind="attrs" v-on="on">
              ADD {{ userRole.toUpperCase() }}
            </v-btn>
          </template>
          <v-card>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-text-field
                    v-model="addedUserId"
                    label="New User ID"
                    type="number"
                  ></v-text-field>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeAddMemberDialog">
                Cancel
              </v-btn>
              <v-btn color="blue darken-1" text @click="saveAddedMember">
                Add
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogDeleteMember" max-width="510px">
          <v-card>
            <v-card-title class="text-h5"
              >Are you sure you want to remove {{ addedUserId }}?</v-card-title
            >
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeDeleteMember"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="deleteMemberConfirm"
                >OK</v-btn
              >
              <v-spacer></v-spacer>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-toolbar>
    </template>
    <template v-slot:[`item.actions`]="{ item }">
      <v-icon small @click="deleteMember(item)"> mdi-delete </v-icon>
    </template>
  </v-data-table>
</template>

<script>
import { AXIOS } from "../http-common.js";
export default {
  props: ["userRole", "courseCode"],
  data: () => ({
    addMemberDialog: false,
    dialogDeleteMember: false,
    userHeaders: [
      {
        text: "ID",
        align: "start",
        value: "id",
      },
      {
        text: "Name",
        value: "name",
      },
      { text: "Actions", value: "actions", sortable: false },
    ],
    deletedMemberIndex: -1,
    isAddingNewCourse: true,
    addedUserId: "",
    members: [],
  }),

  watch: {
    addMemberDialog(val) {
      val || this.closeAddMemberDialog();
    },
    dialogDeleteMember(val) {
      val || this.closeDeleteMember();
    },
  },

  created() {
    this.initialize();
  },

  methods: {
    async initialize() {
      // get members from the server
      await AXIOS.get(
        `admin/courseMembers?courseCode=${this.courseCode}&role=${this.userRole}`,
        {}
      ).then((res) => {
        this.members = res.data;
      });
    },
    deleteMember(item) {
      this.deletedMemberIndex = this.members.indexOf(item);
      this.addedUserId = item.id;
      this.dialogDeleteMember = true;
    },
    async deleteMemberConfirm() {
      let response;
      await AXIOS.delete(
        `admin/removeFromCourse?userId=${this.addedUserId}&courseCode=${this.courseCode}`,
        {}
      ).then((res) => {
        response = res.data;
      });
      if (response) this.members.splice(this.deletedMemberIndex, 1);
      this.closeDeleteMember();
    },
    closeAddMemberDialog() {
      this.addMemberDialog = false;
      this.addedUserId = "";
    },
    closeDeleteMember() {
      this.dialogDeleteMember = false;
      this.addedUserId = "";
    },
    async saveAddedMember() {
      // Send id
      // this.addedUserId
      let response;
      await AXIOS.post(
        `admin/addToCourse?userId=${this.addedUserId}&courseCode=${this.courseCode}&role=${this.userRole}`,
        {}
      ).then((res) => {
        response = res.data;
      });
      console.log(response);
      if (response != null) {
        this.members.push({ id: this.addedUserId, name: response });
        this.closeAddMemberDialog();
      }
      // get user name
      // if success add it to the table
    },
  },
};
</script>

<style></style>
