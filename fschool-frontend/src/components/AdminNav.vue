<template>
    <div style="float:left">
        <div>
            <v-navigation-drawer
                permanent
                expand-on-hover
            >
                <v-list>
                <v-list-item class="px-2">
                    <v-list-item-avatar>
                    <v-img src="@/assets/admin_avatar.svg"></v-img>
                    </v-list-item-avatar>
                </v-list-item>

                <v-list-item link>
                    <v-list-item-content>
                    <v-list-item-title class="text-h6">
                        Admin
                    </v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                </v-list>

                <v-divider></v-divider>

                <v-list
                nav
                dense
                >
                <v-list-item link>
                    <v-list-item-icon>
                        <v-list-item-avatar>
                            <v-img src="@/assets/add_course_icon.svg"/>
                        </v-list-item-avatar>
                    </v-list-item-icon>
                    <v-list-item-title>Add / Edit Course</v-list-item-title>
                </v-list-item>
                <v-list-item link>
                    <v-list-item-icon>
                        <v-list-item-avatar>
                            <v-img src="@/assets/teacher_avatar.svg"/>
                        </v-list-item-avatar>
                    </v-list-item-icon>
                    <v-list-item-title>Add Teacher</v-list-item-title>
                </v-list-item>
                <v-list-item link>
                    <v-list-item-icon>
                        <v-list-item-avatar>
                            <v-img src="@/assets/student_avatar.svg"/>
                        </v-list-item-avatar>
                    </v-list-item-icon>
                    <v-list-item-title>Add Student</v-list-item-title>
                </v-list-item>
                </v-list>
            </v-navigation-drawer>
            </div>
        </div>
</template>
<script>
export default ({
    
})
</script>
