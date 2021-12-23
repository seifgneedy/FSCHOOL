<template>
    <div>
        <v-data-table
        style="margin:25px"
        :headers="headers"
        :items="users"
        :loading="loading"
        loading-text="Loading... Please wait"
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
                            <v-col cols="12" sm="6" md="4">
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
                                  :error-messages="birthDateErrors"
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
                  <v-alert 
                  v-show="userExists"
                  type="error"
                  style="font-size:19px;font-weight:bold" 
                  >User With The Same Email Already Exists</v-alert>
                  <v-spacer />
                  <v-btn
                  @click="close" class="mr-5" dark color="error"
                  >CLOSE</v-btn>
                  <v-btn
                  @click="addUser" class="mr-5" dark color="success"
                  >SAVE</v-btn>
                </v-card-actions>
            </v-card>
            </v-dialog>
            <v-dialog v-model="dialogDelete" max-width="600px">
              <v-card align="center">
                <v-card-title class="text-h6">
                  Are you sure you want to delete {{userRole}} with ID: {{newUser.id}}
                  and Email : {{newUser.email}}
                </v-card-title>
                <v-card-actions>
                  <v-spacer />
                  <v-btn color="blue darken-1" dark @click="closeDelete">Cancel</v-btn>
                  <v-btn color="blue darken-1" dark @click="deleteUserConfirm">OK</v-btn>
                  <v-spacer />
                </v-card-actions>
              </v-card>
            </v-dialog>
            </v-toolbar>
        </template>
        <template v-slot:[`item.actions`]=" { item }">
          <v-icon
          small
          @click="deleteUser(item)"
        >
        mdi-delete
      </v-icon>
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
        loading:false,
        dialogDelete: false,
        editedUserIndex:-1,
        userExists:false,
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
            birthDate:"2021-4-2"
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
    created(){
      this.loading=true;
      this.getUsers();
      this.loading=false;
    },
    methods:{
        async getUsers(){
            await AXIOS.get(`admin/${this.userRole}s`).then(res=>{
                this.users=res.data;
            });
        },
        async addUser(){
            this.$v.$touch();
            if(
              this.$v.newUser.email.$invalid ||
              this.$v.newUser.password.$invalid ||
              this.$v.newUser.firstName.$invalid ||
              this.$v.newUser.lastName.$invalid ||
              this.$v.newUser.sex.$invalid ||
              this.$v.newUser.birthDate.$invalid 
            )
              return ;
            this.newUser.role=this.userRole;
            console.log(this.newUser);
            let response;
            await AXIOS.post('admin/user',this.newUser).then(res=>{
                response=res.data;
            });
            console.log(response);
            if(response!=0){
              this.userExists=false;
              this.newUser.id=response;
              this.users.push(this.newUser);
              this.close();
            }else{
              this.userExists=true;
            }
        },
        close(){
          this.$v.$reset();
          this.dialog=false;
          this.$nextTick(()=>{
            this.newUser=Object.assign({},this.defaultUser);
          });
        },
        closeDelete(){
          this.dialogDelete=false;
          this.$nextTick(()=>{
            this.newUser=Object.assign({},this.defaultUser);
          });
        },
        deleteUser(user){
          this.editedUserIndex=this.users.indexOf(user);
          this.newUser=Object.assign({},user);
          this.dialogDelete=true;
        },
        async deleteUserConfirm(){
          let response;
          await AXIOS.delete(`admin/user?id=${this.newUser.id}`).then(res=>{
            response=res.data;
          });
          if(response){
            // deleted successfully
            this.users.splice(this.editedUserIndex,1);
          }else{
            // couldn't delete it
          }
          this.closeDelete();
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
      !this.$v.newUser.firstName.minLength && errors.push("Invalid First Name");
      !this.$v.newUser.firstName.required && errors.push("First Name is required.");
      return errors;
    },
    lastNameErrors(){
      const errors=[];
      if(!this.$v.newUser.lastName.$dirty) return errors;
      !this.$v.newUser.lastName.minLength && errors.push("Invalid Last Name");
      !this.$v.newUser.lastName.required && errors.push("Last Name is required.");
      return errors;
    },
    sexErrors() {
      const errors = [];
      if (!this.$v.newUser.sex.$dirty) return errors;
      !this.$v.newUser.sex.required && errors.push("Sex is required");
      return errors;
    },  
    birthDateErrors() {
      const errors = [];
      if (!this.$v.newUser.birthDate.$dirty) return errors;
      !this.$v.newUser.birthDate.required && errors.push("Sex is required");
      return errors;
    },
  }
}
</script>

<style>

</style>