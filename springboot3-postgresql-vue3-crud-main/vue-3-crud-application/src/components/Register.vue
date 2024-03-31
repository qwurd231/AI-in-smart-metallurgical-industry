<template>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <h3>注册</h3>
                <form @submit="validateAndSubmit">
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
                    <button type="submit" class="btn btn-success">注册</button>
                    <router-link to="/login" class="btn btn-link">登录</router-link>
                </form>
            </div>
        </div>
    </div>

</template>

<script>
import UserDataService from "../service/UserDataService";
export default {
    name: "Register",
    data() {
        return {
            user: {
                id: "",
                name: "",
                email: "",
                password: "",
            },
            errors: []
        };
    },
    methods: {
        refreshUserDetails() {
            UserDataService.retrieveUser(this.id).then((res) => {
                this.name = res.data.name;
                this.email = res.data.email;
                this.password = res.data.password;
            });
        },
        validateAndSubmit(e) {
            e.preventDefault()
            this.errors = [];
            if (!this.user.name) {
                this.errors.push("Enter valid values");
            }
            UserDataService.findUserByName(this.user.name).then((res) => {
                if (res.data.name === this.user.name) {
                    this.errors.push("User already exists");
                }
            });
            if (this.errors.length === 0) {
                UserDataService.createUser({
                    name: this.user.name,
                    email: this.user.email,
                    password: this.user.password,
                }).then((res) => {
                    this.user.id = res.data.id;
                    this.$router.push(`/user/${this.user.id}/data`);
                });
            }
        }
    }
};
</script>