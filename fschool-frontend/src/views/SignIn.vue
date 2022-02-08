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
            v-model="email"
            :error-messages="EmailErrors"
            name="Email"
            label="Email"
            append-icon="mdi-close"
            filled
            required
            @click:append="email = ''"
            @input="$v.email.$touch()"
            @blur="$v.email.$touch()"
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
            @keydown.enter="signIn"
          ></v-text-field>
          <br />
          <v-btn class="mr-15" color="success" @click="signIn">Sign In</v-btn>
          <v-btn color="warning" @click="clear">Clear</v-btn>
          <br />
          <br />
          <v-alert
            v-show="alert"
            type="error"
            style="font-size: 19px; font-weight: bold"
            >Wrong Email or Password</v-alert
          >
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
  email,
} from "vuelidate/lib/validators";
import { AXIOS } from "../http-common.js";

export default {
  name: "SignIn",
  data() {
    return {
      show: false,
      email: "",
      password: "",
      alert: false,
    };
  },
  validations: {
    email: {
      email,
      required,
    },
    password: {
      required,
      minLength: minLength(8),
      maxLength: maxLength(20),
    },
  },
  methods: {
    async signIn() {
      this.$v.$touch();
      if (this.$v.email.$invalid || this.$v.password.$invalid) return;
      let response;
      await AXIOS.post("sign-in", {
        Email: this.email,
        Password: this.password,
      }).then((res) => {
        response = res.data;
      });
      if (response === "Incorrect Credentials") {
        this.alert = true;
      } else {
        this.alert = false;
        this.$store.commit("setUser", {
          email: this.email,
          role: response,
        });
        if (response == "admin") await this.$router.push("/admin");
        else if (response == "student") await this.$router.push("/student");
        else if (response == "teacher") await this.$router.push("/teacher");
      }
    },
    clear() {
      this.$v.$reset();
      this.alert = false;
      this.email = "";
      this.password = "";
    },
    initialize() {
      this.clear();
      this.$store.commit("signOut");
    },
  },
  created() {
    this.initialize();
  },
  computed: {
    EmailErrors() {
      const errors = [];
      if (!this.$v.email.$dirty) return errors;
      !this.$v.email.email && errors.push("Invalid Email");
      !this.$v.email.required && errors.push("Email is required.");
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
  },
};
</script>
