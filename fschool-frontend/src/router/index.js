import Vue from "vue";
import VueRouter from "vue-router";
import SignIn from "../views/SignIn.vue";
import AdminView from "../views/admin.vue";
import StudentView from "../views/Student.vue";
import TeacherView from "../views/Teacher.vue";
import CourseView from "../views/Course.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "SignIn",
    component: SignIn,
  },
  {
    path: "/admin",
    name: "admin",
    component: AdminView,
  },
  {
    path: "/student",
    name: "student",
    component: StudentView,
  },
  {
    path: "/teacher",
    name: "teacher",
    component: TeacherView,
  },
  {
    path: "/student/:code",
    name: "course",
    component: CourseView,
  },
  // {
  //   path: "/about",
  //   name: "About",
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "about" */ "../views/About.vue"),
  // },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
