<template>
    <div class="container">
        <h3>数据</h3>
        <br>
        <div class="container">
            <h4>用户</h4>
            <h4>{{ this.user.name }}</h4>
            <a href="C:\Users\36049\OneDrive\file\magazines\WSJ\TheWallStreetJournal2022.07.05_downmagaz.net">返回</a>
        </div>
        <div class="container">
            <table class="table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>模型</th>
                    <th>终点温度(℃)</th>
                    <th>终点碳含量</th>
                    <th>终点硅含量</th>
                    <!-- Add more columns for each parameter -->
                    <th>更新</th>
                    <th>删除</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="dataSet in user.dataSets" v-bind:key="dataSet.id">
                    <td >{{ dataSet.id }}</td>
                    <td>{{ dataSet.selectedModel }}</td>
                    <td v-if="dataSet.output1 !== 0">{{ dataSet.output1 }}</td>
                    <td v-else>-</td>
                    <td v-if="dataSet.output2 !== 0">{{ dataSet.output2 }}</td>
                    <td v-else>-</td>
                    <td v-if="dataSet.output3 !== 0">{{ dataSet.output3 }}</td>
                    <td v-else>-</td>
                    <td>
                        <button class="btn btn-warning" v-on:click="updateDataSet(dataSet.id)">
                            更新
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-danger" v-on:click="deleteDataSet(dataSet.id)">
                            删除
                        </button>
                    </td>
                </tr>
                </tbody>
            </table>
            <div class="row">
                <button class="btn btn-success" v-on:click="addDataSet()">Add</button>
            </div>
        </div>
    </div>
</template>

<script>
import DataSetService from "../service/DataSetService";
import UserDataService from "../service/UserDataService";

export default {
    name: "Data",
    data() {
        return {
            user: {
                id: "",
                name: "",
                email: "",
                password: "",
                dataSets: [],
                timer: null,
                firstTime: true,
            },
        };
    },
    methods: {
        refreshDataSets() {
            this.user.id = this.$route.params.user_id;
            UserDataService.retrieveUser(this.user.id).then((res) => {
                this.user.name = res.data.name;
                this.user.email = res.data.email;
                this.user.password = res.data.password;
            });
            DataSetService.retrieveDataSetByUserId(this.user.id).then((res) => {
                this.user.dataSets = res.data;
            });
        },
        addDataSet() {
            this.$router.push(`/user/${this.user.id}/data/-1`);
        },
        updateDataSet(id) {
            this.$router.push(`user/${this.user.id}/data/${id}`);
        },
        deleteDataSet(id) {
            DataSetService.deleteDataSet(id).then(() => {
                this.refreshDataSets();
            });
        },
    },
    created() {
        this.refreshDataSets();
/*
        if (this.user.firstTime) {
            this.user.firstTime = false;
            console.log("first time", this.user.firstTime);
            this.user.timer = setTimeout(() => {
                this.refreshDataSets();
            }, 3000);
        }*/

    },
    /*updated() {
        this.refreshDataSets();
    },*/
};
</script>

<style scoped>

</style>