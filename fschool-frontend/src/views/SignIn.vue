<template>
  <v-app
    :style="{
      backgroundImage: 'url(' + require('@/assets/wallpaper.jpg') + ')',
      backgroundSize: 'cover',
    }"
  >
    <v-main>
      <br /><br /><br /><br />
      <v-card color="#FAFAFA" class="mx-auto" shaped outlined max-width="500">
        <v-card-title>Welcome to FSCHOOL</v-card-title>

        <form style="font-size: 20px; margin: 60px">
          <v-text-field
            v-model="ID"
            :error-messages="IDErrors"
            name="ID"
            label="ID"
            append-icon="mdi-close"
            filled
            required
            @click:append="ID = ''"
            @input="$v.ID.$touch()"
            @blur="$v.ID.$touch()"
          ></v-text-field>
          <v-text-field
            name="Password"
            v-model="password"
            :error-messages="passwordErrors"
            :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
            :type="show ? 'text' : 'password'"
            filled
            label="Password"
            required
            @click:append="show = !show"
            @input="$v.password.$touch()"
            @blur="$v.password.$touch()"
          ></v-text-field>
          <v-select
            v-model="role"
            :error-messages="roleErrors"
            :items="roles"
            required
            label="Role"
            @change="$v.role.$touch()"
            @blur="$v.role.$touch()"
          ></v-select>
          <br />
          <v-btn class="mr-15" color="success" @click="signIn">Sign In</v-btn>
          <v-btn color="warning" @click="clear">Clear</v-btn>
          <br> <br>
          <v-alert 
          v-show="alert" 
          type="error" 
          style="font-size:19px;font-weight:bold" 
          >Wrong ID or Password</v-alert>
        </form>
      </v-card>
    </v-main>
  </v-app>
</template>

<script>
import {
  required,
  minLength,
  maxLength,
  integer,
  minValue
} from "vuelidate/lib/validators";
import { AXIOS } from "../http-common.js";
import router from "../router/index.js";

export default {
  name: "SignIn",
  data() {
    return {
      show: false,
      ID: "",
      password: "",
      roles: ["System Admin"], // the rest to be added in next phases
      role: null,
      alert:false,
    };
  },
  validations: {
    ID: {
      integer,
      minValue:minValue(18010000),
      required,
    },
    password: {
      required,
      minLength: minLength(8),
      maxLength: maxLength(20),
    },
    role: {
      required,
    },
  },
  methods: {
    async signIn() {

      this.$v.$touch();
      if (
        this.$v.ID.$invalid ||
        this.$v.password.$invalid ||
        this.$v.role.$invalid
      )
        return;
      let response;
      await AXIOS.post('sign-in',{
        ID:this.ID,
        Password:this.password,
        Role:this.role
      }).then(res=>{
        response=res.data;
      });
      if(response){
        this.alert=false;
        this.$store.commit("setUser",{
          ID:this.ID,
          Role:this.role
        });
        if(this.role=="System Admin")
          await router.push("/AdminView");
        else if (this.role=="Student")
          await router.push("/user"); // TODO: next phase
        else if (this.role=="Teacher")
          await router.push("/user");
      }else{
        this.alert=true;
        // should show message of wrong ID or password

      }
    },
    clear() {
      this.$v.$reset();
      this.alert=false;
      this.ID = "";
      this.password = "";
      this.role = null;
    },
  },
  computed: {
    roleErrors() {
      const errors = [];
      if (!this.$v.role.$dirty) return errors;
      !this.$v.role.required && errors.push("Role is required");
      return errors;
    },
    passwordErrors() {
      const errors = [];
      if (!this.$v.password.$dirty) return errors;
      !this.$v.password.maxLength &&
        errors.push("Password must be at most 20 characters long");
      !this.$v.password.minLength &&
        errors.push("Password must be at least 8 characters long");
      !this.$v.password.required && errors.push("Password is required.");
      return errors;
    },
    IDErrors() {
      const errors = [];
      if (!this.$v.ID.$dirty) return errors;
      !this.$v.ID.integer && errors.push("ID must be number");
      !this.$v.ID.minValue && errors.push("ID is not valid");
      !this.$v.ID.required && errors.push("ID is required");
      return errors;
    },
  },
};
</script>
