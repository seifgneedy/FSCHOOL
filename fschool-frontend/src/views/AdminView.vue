<template>
    <v-app >
    <v-content>
        <admin-nav  />
        <div align="center">
            <h1>Hello world</h1>
        </div>
    </v-content>
    </v-app>
</template>

<script>
import AdminNav from "@/components/AdminNav.vue";
export default {
    name: "AdminView",
    components:{
        AdminNav,
    }
}
</script>