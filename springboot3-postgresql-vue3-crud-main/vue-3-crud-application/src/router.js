import { createWebHistory, createRouter } from "vue-router";

const routes =  [
    {
        path: "/users",
        name: "Users",
        component: () => import("./components/Users"),
    },
    {
        path: "/user/:id",
        name: "User",
        component: () => import("./components/User"),
    },
    {
        path: "/login",
        name: "Login",
        component: () => import("./components/Login"),
    },
    {
        path: "/register",
        name: "Register",
        component: () => import("./components/Register"),
    },
    {
        path: "/",
        name: "Home",
        component: () => import("./components/Home"),
    },
    {
        path: "/user/:user_id/data/:data_id",
        name: "DataSetSubmit",
        component: () => import("./components/DataSetSubmit"),
    },
    {
        path: "/user/:user_id/data",
        name: "Data",
        component: () => import("./components/Data"),
    }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;