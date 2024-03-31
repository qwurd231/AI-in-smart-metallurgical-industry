<template>
    <div class="container">
        <h3>所有用户</h3>
        <div v-if="message" class="alert alert-success">{{ this.message }}</div>
        <div class="container">
            <table class="table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>姓名</th>
                    <th>邮箱</th>
                    <th>更新</th>
                    <th>删除</th>
                    <th>数据</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="user in users" v-bind:key="user.id">

                    <td>{{ user.id }}</td>
                    <td>{{ user.name }}</td>
                    <td>{{ user.email }}</td>
                    <td>
                        <button class="btn btn-warning" v-on:click="updateUser(user.id)">
                            更新
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-danger" v-on:click="deleteUser(user.id)">
                            删除
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-outline-success" v-on:click="dataOfUser(user.id)">
                            数据
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="row">
                <button class="btn btn-success" v-on:click="addUser()">Add</button>
            </div>
        </div>
    </div>
</template>
<script>
import UserDataService from "../service/UserDataService";

export default {
    name: "Users",
    data() {
        return {
            users: [],
            message: "",
        };
    },
    methods: {
        refreshUsers() {
            UserDataService.retrieveAllUsers().then((res) => {
                this.users = res.data;
            });
        },
        addUser() {
            this.$router.push(`/user/-1`);
        },
        updateUser(id) {
            this.$router.push(`/user/${id}`);
        },
        deleteUser(id) {
            UserDataService.deleteUser(id).then(() => {
                this.refreshUsers();
            });
        },
        dataOfUser(id) {
            this.$router.push(`/user/${id}/data`);
        },
    },
    created() {
        this.refreshUsers();
    },
};
</script>