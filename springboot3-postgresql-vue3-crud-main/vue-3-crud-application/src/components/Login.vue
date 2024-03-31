<!-- write login page -->
<template>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h3>登录</h3>
                <form @submit="check">
                    <div class="form-group">
                        <label>姓名</label>
                        <input type="text" class="form-control" v-model="user.name">
                    </div>
                    <div class="form-group">
                        <label>邮箱</label>
                        <input type="text" class="form-control" v-model="user.email">
                    </div>
                    <div class="form-group">
                        <label>密码</label>
                        <input type="text" class="form-control" v-model="user.password">
                    </div>
                    <button type="submit" class="btn btn-primary" v-on:click="login()">登录</button>
                    <router-link to="/register" class="btn btn-link">注册</router-link>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import UserDataService from "@/service/UserDataService";

export default {
    name: "Login",
    data() {
        return {
            user: {
                name: "",
                email: "",
                password: ""
            },
            errors: []
        };
    },
    methods: {
        async check(e) {
            e.preventDefault();
            this.errors = [];

            const res = await UserDataService.findUserByName(this.user.name);

            if (res.data.length === 0) {
                alert("Invalid credentials");
                console.log("Invalid credentials");
                this.errors.push("Invalid credentials");
            } else {
                let password = "";
                for (let i = 0; i < res.data.length; i++) {
                    if (this.user.name === res.data[i].name) {
                        this.user.id = res.data[i].id;
                        password = res.data[i].password;
                        break;
                    }
                }
                if (this.user.password !== password) {
                    alert("Invalid credentials");
                    this.errors.push("Invalid credentials");
                }
            }

            console.log(this.errors);
            console.log("one", this.user.id);
            return this.errors.length === 0;

        },
        async login() {
            const isValid = await this.check(event);
            console.log(isValid);
            console.log(this.user.id);
            if (isValid) {
                this.$router.push(`/user/${this.user.id}/data`);
            }
        }
    },
};
</script>

<style scoped>
</style>