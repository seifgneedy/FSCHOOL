<template>
    <div>
        <!-- <p>{{getUsers()}}</p> -->
        <v-data-table
        style="margin:25px"
        :headers="headers"
        :items="users"
        sort-by="id"
        class="elevation-1"
        >
        <template v-slot:top >
            <v-toolbar flat>
                <v-toolbar-title 
                style="font-weight:bold">
                    {{userRole.toUpperCase()}}S
                </v-toolbar-title>
                <v-divider
                class="mx-4"
                inset
                vertical
                ></v-divider>
            <v-spacer ></v-spacer>
            <v-dialog
            persistent
            v-model="dialog" 
            max-width="800px">
                <template v-slot:activator="{on,attrs}">
                    <v-btn
                    color="primary"
                    dark
                    class="mb-2"
                    v-bind=attrs
                    v-on="on">
                    Add New {{userRole}}</v-btn>
                </template>
                <v-card>
                <v-card-title>ADD NEW {{userRole.toUpperCase()}}</v-card-title>
                <v-card-text>
                    <v-container>
                        <v-row>
                          <v-col cols="12" sm="6" md="4">
                              <v-text-field
                              name="First Name"
                              label="First Name"
                              v-model="newUser.firstName"
                              filled
                              @input="$v.newUser.firstName.$touch()"
                              @blur="$v.newUser.firstName.$touch()"
                              :error-messages="firstNameErrors"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                              <v-text-field
                              name="Last Name"
                              label="Last Name"
                              v-model="newUser.lastName"
                              filled
                              @input="$v.newUser.lastName.$touch()"
                              @blur="$v.newUser.lastName.$touch()"
                              :error-messages="lastNameErrors"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                                <v-text-field
                                name="Password"
                                v-model="newUser.password"
                                :error-messages="passwordErrors"
                                filled
                                label="Password"
                                required
                                @input="$v.newUser.password.$touch()"
                                @blur="$v.newUser.password.$touch()"
                                ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="10">
                              <v-text-field
                              name="Email"
                              label="Email"
                              v-model="newUser.email"
                              filled
                              @input="$v.newUser.email.$touch()"
                              @blur="$v.newUser.email.$touch()"
                              :error-messages="EmailErrors"
                              ></v-text-field>
                            </v-col>
                            <v-col cols="12" sm="6" md="4">
                              <v-select
                              v-model="newUser.sex"
                              label="Sex"
                              :items="['Male','Female']"
                              :error-messages="sexErrors"
                              @change="$v.newUser.sex.$touch()"
                              @blur="$v.newUser.sex.$touch()"
                              ></v-select>
                            </v-col>
                            <v-col>
                              <v-menu
                                ref="menu"
                                v-model="birthMenu"
                                :close-on-content-click="false"
                                transition="scale-transition"
                                offset-y
                                min-width="auto"
                              >
                                <template v-slot:activator="{ on, attrs }">
                                  <v-text-field
                                    v-model="newUser.birthDate"
                                    label="Birthday date"
                                    prepend-icon="mdi-calendar"
                                    readonly
                                    v-bind="attrs"
                                    v-on="on"
                                  ></v-text-field>
                                </template>
                                <v-date-picker
                                  v-model="newUser.birthDate"
                                  :max="(new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10)"
                                  min="1950-01-01"
                                  @change="$refs.menu.save(newUser.birthDate)"
                                ></v-date-picker>
                              </v-menu>
                            </v-col>
                        </v-row>
                    </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer />
                  <v-btn
                  @click="addUser"></v-btn>
                </v-card-actions>
            </v-card>
            </v-dialog>
            </v-toolbar>
        </template>
        </v-data-table>
    </div>
</template>

<script>
import {
    required,
    minLength,
    maxLength,
    email,
} from "vuelidate/lib/validators";
import { AXIOS } from "../http-common.js";
export default {
    props:['userRole'],
    data:()=>({
        dialog: false,
        dialogDelete: false,
        newUser:{
            id:'',password:'',email:'',firstName:'',lastName:'',sex:'',birthDate:''
        },
        defaultUser:{
            id:'',password:'',email:'',firstName:'',lastName:'',sex:'',birthDate:''
        },
        users:[{
            id:"18010834",
            email:"seifgneedy@gmail.com",
            firstName:"Seif",
            lastName:"Gneedy",
            sex: "M",
            birthDate:"22-4-2021"
        }],
        headers:[
            {text:"ID", value:"id" ,align:"start"},
            {text:"Email",value:"email"},
            {text:"First Name",value:"firstName"},
            {text:"Last Name",value:"lastName"},
            {text:"Sex",value:"sex"},
            {text:"Birth Date",value:"birthDate"},
            {text:"Actions",value:"actions",sortable:false}
        ],
        birthMenu:false,
    }),
    validations:{
        newUser:{
            email:{
                required,
                email
            },
            firstName:{
                required,
                minLength:minLength(3)
            },
            lastName:{
                required,
                minLength:minLength(3)
            },
            sex:{
                required
            },
            birthDate:{
                required
            },
            password: {
              required,
              minLength: minLength(8),
              maxLength: maxLength(20),
          },
        }
    },
    methods:{
        async getUsers(){
            await AXIOS.get(`admin/${this.userRole}s`).then(res=>{
                this.users=res.data;
            });
            this.users;
        },
        async addUser(newUser){
            newUser.role=this.userRole;
            let response;
            await AXIOS.post('admin/addUser',newUser).then(res=>{
                response=res.data;
            });
            console.log(response);
            // TODO:check response=> if valid add it to users table
            // false=> show an error
        }
    },
  computed:{
    passwordErrors() {
      const errors = [];
      if (!this.$v.newUser.password.$dirty) return errors;
      !this.$v.newUser.password.maxLength &&
        errors.push("Password must be at most 20 characters long");
      !this.$v.newUser.password.minLength &&
        errors.push("Password must be at least 8 characters long");
      !this.$v.newUser.password.required && errors.push("Password is required.");
      return errors;
    },
    EmailErrors(){
      const errors=[];
      if(!this.$v.newUser.email.$dirty) return errors;
      !this.$v.newUser.email.email && errors.push("Invalid Email");
      !this.$v.newUser.email.required && errors.push("Email is required.");
      return errors;
    },
    firstNameErrors(){
      const errors=[];
      if(!this.$v.newUser.firstName.$dirty) return errors;
      !this.$v.newUser.email.firstName && errors.push("Invalid Email");
      !this.$v.newUser.email.required && errors.push("Email is required.");
      return errors;
    },
    lastNameErrors(){
      const errors=[];
      if(!this.$v.newUser.lastName.$dirty) return errors;
      !this.$v.newUser.email.lastName && errors.push("Invalid Email");
      !this.$v.newUser.email.required && errors.push("Email is required.");
      return errors;
    },
    sexErrors() {
      const errors = [];
      if (!this.$v.newUser.sex.$dirty) return errors;
      !this.$v.newUser.sex.required && errors.push("Role is required");
      return errors;
    },
  }
}
</script>

<style>

</style>