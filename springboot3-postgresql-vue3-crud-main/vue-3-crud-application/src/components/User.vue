<template>
    <div>
        <h3>User</h3>
        <div class="container">
            <form @submit="validateAndSubmit">
                <div v-if="errors.length">
                    <div
                        class="alert alert-danger"
                        v-bind:key="index"
                        v-for="(error, index) in errors"
                    >
                        {{ error }}
                    </div>
                </div>
                <fieldset class="form-group">
                    <label>Name</label>
                    <input type="text" class="form-control" v-model="name"/>
                </fieldset>
                <fieldset class="form-group">
                    <label>Email Id</label>
                    <input type="text" class="form-control" v-model="email"/>
                </fieldset>
                <fieldset class="form-group">
                    <label>Password</label>
                    <input type="text" class="form-control" v-model="password"/>
                </fieldset>
                <button class="btn btn-success" type="submit">Save</button>
            </form>
        </div>
    </div>
</template>
<script>
import UserDataService from "../service/UserDataService";

export default {
    name: "User",
    data() {
        return {
            name: "",
            email: "",
            password: "",
            errors: [],
        };
    },
    computed: {
        id() {
            return Number(this.$route.params.id);
        },
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
            e.preventDefault();
            this.errors = [];
            if (!this.name) {
                this.errors.push("Enter valid values");
            }
            if (this.errors.length === 0) {
                if (this.id === -1) {
                    UserDataService.createUser({
                        name: this.name,
                        email: this.email,
                        password: this.password,
                    }).then(() => {
                        this.$router.push("/users");
                    });
                } else {
                    console.log("this.id", this.id);
                    UserDataService.updateUser(this.id, {
                        id: this.id,
                        name: this.name,
                        email: this.email,
                        password: this.password,
                    }).then(() => {
                        this.$router.push("/users");
                    });
                }
            }
        },
    },
    created() {
        this.refreshUserDetails();
    },
};
</script>