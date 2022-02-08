<template>
  <v-container fluid>
    <v-row justify="end">
      <v-col>
        <v-card-title
          class="text-h3 text--primary font-weight-bold"
          @click="initialize()"
          >Assignments</v-card-title
        >
      </v-col>
      <v-col>
        <v-btn icon @click="initialize()" color="primary" dark>
          <v-icon>mdi-refresh</v-icon>
        </v-btn>
        <v-dialog
          v-model="Assdialog"
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
              @click="addassError = false"
            >
              <v-icon>mdi-plus</v-icon>
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="text-h5">NEW ASSIGNMENT</span>
            </v-card-title>
            <v-card-text>
              <v-container>
                <v-row>
                  <v-col cols="12" sm="6" md="4"></v-col>
                  <v-col cols="12" sm="6" md="4">
                    <v-text-field
                      name="Title"
                      label="Title"
                      :error-messages="titleError"
                      v-model="newAsg.title"
                      @input="$v.newAsg.title.$touch()"
                      @blur="$v.newAsg.title.$touch()"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="12">
                    <v-textarea
                      name="Body"
                      label="Body"
                      :error-messages="assBodyError"
                      v-model="newAsg.body"
                      outlined
                      type="text"
                      @input="$v.newAsg.body.$touch()"
                      @blur="$v.newAsg.body.$touch()"
                      required
                    ></v-textarea>

                    <v-menu
                      ref="menu"
                      v-model="dateMenu"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                    
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="newAsg.dueDate"
                          label="Due date"
                          prepend-icon="mdi-calendar"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-date-picker
                        :error-messages="dueDateErrors"
                        v-model="newAsg.dueDate"
                        :min="
                          new Date(
                            Date.now() - new Date().getTimezoneOffset() * 60000
                          )
                            .toISOString()
                            .substr(0, 10)
                        "
                        @input="$v.newAsg.dueDate.$touch()"
                        @blur="$v.newAsg.dueDate.$touch()"
                        @change="$refs.menu.save(newAsg.dueDate)"
                      ></v-date-picker>
                    </v-menu>
                    <v-menu
                     ref="menu"
                      v-model="timeMenu"
                      :close-on-content-click="false"
                      transition="scale-transition"
                      offset-y
                      min-width="auto"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-text-field
                          v-model="time"
                          label="Due time"
                          prepend-icon="mdi-alarm"
                          readonly
                          v-bind="attrs"
                          v-on="on"
                        ></v-text-field>
                      </template>
                      <v-time-picker format="24hr" use-seconds   v-model="time"></v-time-picker>

                    </v-menu>
                  </v-col>
                </v-row>
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-alert
                v-show="addassError"
                type="error"
                style="font-size: 19px; font-weight: bold"
                >Error adding assignment:{{ assError }}</v-alert
              >
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="closeAssdialog()"
                >Cancel</v-btn
              >
              <v-btn color="blue darken-1" text @click="addAsg()">Add</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
    <v-col v-for="assignment in assignments" :key="assignment.id">
      <v-card color="#0F0639" dark max-width="600">
        <v-card-title>
          <v-icon large left></v-icon>

          <span
            class="text-h4 font-weight-bold"
            v-text="assignment.title"
          ></span>
          <v-spacer></v-spacer>
          <span
            class="text-h6 font-weight-bold"
            v-text="assignment.course.code"
          ></span>
        </v-card-title>

        <v-card-text
          class="text-h5 font-weight-bold"
          v-text="assignment.body"
        ></v-card-text>

        <v-card-actions>
          <v-list-item class="grow">
            <span class="subheading mr-2">{{
              new Date(assignment.assigned_On).toLocaleString("en-GB")
            }}</span>
            <v-row align="center" justify="end">
              <span>Due by:</span>
              <span class="subheading mr-2">{{
                new Date(assignment.dueDate).toLocaleString("en-GB")
              }}</span>

              <span class="mr-1">||</span>
              <v-icon
                class="mr-1"
                @click="opendeliverable()"
                v-show="canAdd"
              >
                mdi-comment-multiple-outline
              </v-icon>

              <v-dialog
                v-model="addworkdialog"
                persistent
                :retain-focus="false"
                max-width="600px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    icon
                    color="primary"
                    dark
                    v-bind="attrs"
                    v-on="on"
                    v-show="canaddwork"
                    @click="addworkError = false, openAssignment(assignment.id)"
                  >
                    <v-icon>mdi-cloud-upload</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <v-card-title>
                    <span class="text-h5">AddWork</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-row>
                        <v-col cols="12" sm="6" md="4"></v-col>
                        <v-col cols="12">
                          <v-textarea
                            name="body"
                            label="body / link for your work"
                            :error-messages="workBodyError"
                            v-model="newdeliver.body"
                            outlined
                            type="text"
                            @input="$v.newdeliver.body.$touch()"
                            @blur="$v.newdeliver.body.$touch()"
                            required
                          ></v-textarea>
                        </v-col>
                      </v-row>
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-alert
                      v-show="addworkError"
                      type="error"
                      style="font-size: 19px; font-weight: bold"
                      >Error adding work:{{ workError }}</v-alert
                    >
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="closeworkDialog()"
                      >Cancel</v-btn
                    >
                    <v-btn
                      color="blue darken-1"
                      text
                      @click="addwork()"
                      >Submit</v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-dialog>
              <v-dialog
                v-model="showdeliverable"
                max-width="800px"
                :retain-focus="false"
              >
                <v-card align="center">
                  <v-col
                    v-for="deliverable in deliverables"
                    :key="deliverable.id"
                  >
                    <v-card
                      class="mx-auto"
                      color="#0F0639"
                      dark
                      max-width="600"
                    >
        <v-card-title>
          <v-icon large left></v-icon>

          <span
          ></span>
          <v-spacer></v-spacer>
          <span
            class="text-h6 font-weight-bold"
            v-text="'Grade: ' + deliverable.grade"
          ></span>
        </v-card-title>
                      <v-card-text
                        class="text-h4 font-weight-bold"
                        v-text="deliverable.body"
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
                                deliverable.user.firstName +
                                " " +
                                deliverable.user.lastName
                              }}
                            </v-text>
                          </v-card-action>
                          <v-row align="center" justify="end">
                            <span class="subheading mr-2"
                              >{{
                                new Date(
                                  deliverable.submissionDate
                                ).toLocaleString("en-GB")
                              }}
                            </span>
                          </v-row>
                        </v-list-item>
                      </v-card-actions>
                    </v-card>
                  </v-col>
                  <v-card-actions>
                    <v-spacer />
                    <v-btn
                      color="blue darken-1"
                      dark
                      @click="closedeliverable()"
                      >Close</v-btn
                    >
                  </v-card-actions>
                </v-card>
              </v-dialog>
            </v-row>
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
  name: "CourseAssignments",
  props: ["userRole"],
  components: {},
  watch: {
    userRole: {
      immediate: true,
      handler() {
        if (this.userRole == "student") this.canAdd = false;
        else {
          this.canAdd = true;
          this.canaddwork = false;
        }
        this.initialize();
      },
    },
  },
  data: () => ({
    assignmentId: "",
    time: "23:59:59",
    timeMenu: false,
    canAdd: true,
    canaddwork: true,
    showdeliverable: false,
    assignments: [],
    Assdialog: false,
    deliverables: [],
    addworkdialog: false,
    addworkError: false,
    workError: "",
    assError: "",
    addassError: false,
    dateMenu: false,
    currentCourse: "",
    newAsg: {
      dueDate: "",
      title: "",
      body: "",
    },
    newdeliver: {
      body: "",
    },
    defultnewdeliver: {
      body: "",
    },
    defultnewAsg: {
      dueDate: "",
      title: "",
      body: "",
    },
  }),
  validations: {
    newAsg: {
      title: {
        required,
      },
      body: {
        required,
      },
      dueDate: {
        required,
      },
    },
    newdeliver: {
      body: {
        required,
      },
    },
  },

  methods: {

    async initialize() {
      this.assignments = [];
      this.currentCourse = this.$route.params.code;

      if (typeof this.currentCourse === "undefined") {
        this.canAdd = false;
        await AXIOS.get("assignments", {}).then((res) => {
          this.assignments = res.data;
        });
      } else {
        await AXIOS.get(
          `courseAssignments/?courseCode=${this.currentCourse}`,
          {}
        ).then((res) => {
          this.assignments = res.data;
        });
      }
    },
    openAssignment(id){
      this.assignmentId = id;
    },
    async opendeliverable(AssgId) {
      await AXIOS.get(
        `assignmentDeliverables/?assignmentId=${AssgId}`,
        {}
      ).then((res) => {
        this.deliverables = res.data;
      });
      this.showdeliverable = true;
    },
    //sending assignment
    async addAsg() {
      this.newAsg.dueDate = this.newAsg.dueDate + " " + this.time;
      this.$v.$touch();
      if (
        this.$v.newAsg.title.$invalid ||
        this.$v.newAsg.body.$invalid ||
        this.$v.newAsg.dueDate.$invalid
      )
        return;
      let response,
        networkError = false;

      await AXIOS.post(
        `assignment/?courseCode=${this.currentCourse}`,
        this.newAsg
      )
        .then((res) => {
          response = res.data;
        })
        .catch(() => {
          networkError = true;
          response = 0;
        });
      if (response != 0 && response != null) {
        this.closeAssdialog();
        this.assignments.unshift(response);
        this.initialize();
      } else if (networkError) {
        this.addassError = true;
        this.assError = "Network Error,Try Again";
      }
    },
    async addwork() {
      this.$v.$touch();
      if (this.$v.newdeliver.body.$invalid) return;
      let response,
        networkError = false;
      await AXIOS.post(
        `deliverable/?assignmentId=${this.assignmentId}`,
        this.newdeliver.body,
        { headers: { "Content-Type": "text/plain" } }
      )
        .then((res) => {
          response = res.data;
        })
        .catch(() => {
          networkError = true;
          response = 0;
        });
      if (response != 0 && response != null) {
        this.closeworkDialog();
      } else if (networkError) {
        this.addworkError = true;
        this.workError = "Network Error,Try Again";
      }
    },
    closeworkDialog() {
      this.assignmentId = "";
      this.$v.$reset();
      this.addworkdialog = false;
      this.$nextTick(() => {
        this.newdeliver = Object.assign({}, this.defultnewdeliver);
      });
    },
    closedeliverable() {
      this.showdeliverable = false;
    },
    closeAssdialog() {
      this.$v.$reset();
      this.Assdialog = false;
      this.$nextTick(() => {
        this.newAsg = Object.assign({}, this.defultnewAsg);
      });
    },
  },
  computed: {
    titleError() {
      const errors = [];
      if (!this.$v.newAsg.title.$dirty) return errors;
      !this.$v.newAsg.title.required && errors.push("title required");
      return errors;
    },
    assBodyError() {
      const errors = [];
      if (!this.$v.newAsg.body.$dirty) return errors;
      !this.$v.newAsg.body.required && errors.push("body required");
      return errors;
    },
    workBodyError() {
      const errors = [];
      if (!this.$v.newdeliver.body.$dirty) return errors;
      !this.$v.newdeliver.body.required && errors.push("body required");
      return errors;
    },
    dueDateErrors() {
      const errors = [];
      if (!this.$v.newAsg.dueDate.$dirty) return errors;
      !this.$v.newAsg.dueDate.required && errors.push("date required");
      return errors;
    },
  },
};
</script>
