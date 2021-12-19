<template>
  <v-app
  :style="{
        backgroundImage: 'url(' + require('@/assets/wallpaper.jpg') + ')',
        backgroundSize: 'cover',
      }">
    <v-content>
      <br><br><br><br>
      <v-card
      color="#FAFAFA"
      class="mx-auto"
      shaped=true
      outlined
      max-width="500">
      <v-card-title >Welcome to FSCHOOL</v-card-title>
    
      <form style="font-size:20px;margin:60px" >
        <v-text-field 
        v-model="id"
        name="ID" 
        label="ID" 
        append-icon="mdi-close"
        filled
        required
        @click:append="id=''"
        ></v-text-field>
        <v-text-field 
          name="Password" 
          v-model="password"
          :append-icon="show ? 'mdi-eye' : 'mdi-eye-off'"
          :type="show ? 'text' : 'password'"
          filled
          label="Password"
          required
          @click:append="show = !show">
          </v-text-field>
        <v-select
        v-model="role"
        :items=roles
        required
        label="Role"
        ></v-select>
        <br>
        <v-btn 
        class="mr-15"
        color="success"
        @click="signIn"
        >Sign In</v-btn>
        <v-btn
        color="warning">Clear</v-btn>
      </form>
      </v-card>
    </v-content>
  </v-app>
</template>

<script>
export default {
  name: "SignIn",
  data(){
    return{
      show:false,
      id:'',
      password:'',
      roles:['System Admin'],// the rest to be added in next phases
      role:''
    }
  },
  methods:{
    async signIn(){
      //if(valid input){
        // send Post with axios check authenticated 
        // save state if valid and route to its view
        

    //}
    }
  }
};
</script>
